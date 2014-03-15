package com.qopuir.taskcontrol.quartz;

import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_JOB_GROUP_NAME;
import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_JOB_NAME;
import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_JOB_SCHEDULE;

import java.text.ParseException;
import java.util.List;

import org.joda.time.LocalDateTime;
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.scheduling.quartz.JobDetailBean;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.qopuir.taskcontrol.entities.ControlScheduleVO;
import com.qopuir.taskcontrol.entities.enums.ControlScheduleAction;
import com.qopuir.taskcontrol.entities.enums.ControlScheduleStatus;
import com.qopuir.taskcontrol.quartz.jobs.JobClassFactory;
import com.qopuir.taskcontrol.service.ControlScheduleService;

public class TaskControlMainJob extends QuartzJobBean {
	private static final Logger logger = LoggerFactory.getLogger(TaskControlMainJob.class);
	
	private ControlScheduleService controlScheduleService;
	private JobClassFactory jobClassFactory;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		JobDataMap mainJobParameters = context.getMergedJobDataMap();
		
		Scheduler scheduler = context.getScheduler();
		
		List<ControlScheduleVO> controlSchedulesToRun = controlScheduleService.findReadyToStart();

		for (ControlScheduleVO controlScheduleVO : controlSchedulesToRun) {
			logger.debug("ControlSchedule to run: {}", controlScheduleVO.toString());
			
			try {
				if (controlScheduleVO.getStatus() == ControlScheduleStatus.RUNNING) {
					int triggerState = scheduler.getTriggerState(getCronTriggerName(controlScheduleVO), mainJobParameters.getString(PARAM_JOB_GROUP_NAME));
					
					if (triggerState == Trigger.STATE_NONE) {
						logger.info("Scheduling Trigger <{},{}> ...", getCronTriggerName(controlScheduleVO), mainJobParameters.getString(PARAM_JOB_GROUP_NAME));
						
						JobDetail jobDetail = scheduler.getJobDetail(getCronJobName(controlScheduleVO), mainJobParameters.getString(PARAM_JOB_GROUP_NAME));
						
						if (jobDetail == null) {
							jobDetail = getJobDetail(controlScheduleVO, mainJobParameters.getString(PARAM_JOB_GROUP_NAME));
							scheduler.scheduleJob(jobDetail, getCronTrigger(controlScheduleVO, mainJobParameters.getString(PARAM_JOB_GROUP_NAME), jobDetail));
						} else {
							scheduler.scheduleJob(getCronTrigger(controlScheduleVO, mainJobParameters.getString(PARAM_JOB_GROUP_NAME), getCronJobName(controlScheduleVO), mainJobParameters.getString(PARAM_JOB_GROUP_NAME)));
						}
					} else {
						logger.info("Trigger <{},{}> is already runnning", getCronTriggerName(controlScheduleVO), mainJobParameters.getString(PARAM_JOB_GROUP_NAME));
						
						CronTrigger trigger = (CronTrigger) scheduler.getTrigger(getCronTriggerName(controlScheduleVO), mainJobParameters.getString(PARAM_JOB_GROUP_NAME));
						
						if (!trigger.getCronExpression().equals(controlScheduleVO.getCron())) {
							logger.debug("Rescheduling Trigger <{},{}> to {} ...", getCronTriggerName(controlScheduleVO), mainJobParameters.getString(PARAM_JOB_GROUP_NAME), controlScheduleVO.getCron());
							
							scheduler.rescheduleJob(getCronTriggerName(controlScheduleVO), mainJobParameters.getString(PARAM_JOB_GROUP_NAME), getCronTrigger(controlScheduleVO, mainJobParameters.getString(PARAM_JOB_GROUP_NAME), getCronJobName(controlScheduleVO), mainJobParameters.getString(PARAM_JOB_GROUP_NAME)));
						}
					}
				} else {
					logger.info("Scheduling Trigger <{},{}> ...", getCronTriggerName(controlScheduleVO), mainJobParameters.getString(PARAM_JOB_GROUP_NAME));
					
					JobDetail jobDetail = getJobDetail(controlScheduleVO, mainJobParameters.getString(PARAM_JOB_GROUP_NAME));
					scheduler.scheduleJob(jobDetail, getCronTrigger(controlScheduleVO, mainJobParameters.getString(PARAM_JOB_GROUP_NAME), jobDetail));
				}
				
				controlScheduleService.start(controlScheduleVO.getId());
			} catch (SchedulerException e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		List<ControlScheduleVO> controlSchedulesToFinish = controlScheduleService.findReadyToStop();

		for (ControlScheduleVO controlScheduleVO : controlSchedulesToFinish) {
			logger.debug("ControlSchedule to stop: {}", controlScheduleVO.toString());
			
			try {
				int triggerState = scheduler.getTriggerState(getCronTriggerName(controlScheduleVO), mainJobParameters.getString(PARAM_JOB_GROUP_NAME));
				
				if (triggerState != Trigger.STATE_NONE) {
					logger.info("Stopping Trigger <{},{}> ...", getCronTriggerName(controlScheduleVO), mainJobParameters.getString(PARAM_JOB_GROUP_NAME));
					
					scheduler.unscheduleJob(getCronTriggerName(controlScheduleVO), mainJobParameters.getString(PARAM_JOB_GROUP_NAME));
				} else {
					logger.info("Trigger <{},{}> is already stopped", getCronTriggerName(controlScheduleVO), mainJobParameters.getString(PARAM_JOB_GROUP_NAME));
				}
				
				LocalDateTime executionTime = new LocalDateTime().withMillisOfSecond(0);
				
				if (controlScheduleVO.getStatus() == ControlScheduleStatus.PAUSED && executionTime.isAfter(controlScheduleVO.getStart()) && executionTime.isBefore(controlScheduleVO.getEnd())) {
					controlScheduleService.executeAction(controlScheduleVO.getId(), ControlScheduleAction.PAUSE);
				} else {
					controlScheduleService.stop(controlScheduleVO.getId());
				}
			} catch (SchedulerException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	private String getCronTriggerName(ControlScheduleVO controlScheduleVO) {
		return getName("crontrigger", controlScheduleVO);
	}
	
	private String getCronJobName(ControlScheduleVO controlScheduleVO) {
		return getName("cronjob", controlScheduleVO);
	}
	
	private String getName(String prefix, ControlScheduleVO controlScheduleVO) {
		StringBuilder sb = new StringBuilder();
		sb.append(prefix).append("-").append(controlScheduleVO.getControlName().getLiteral()).append("-").append(controlScheduleVO.getId());
		
		return sb.toString();
	}
	
	private JobDetail getJobDetail(ControlScheduleVO controlScheduleVO, String groupName) {
		JobDetailBean jobDetail = new JobDetailBean();
    	jobDetail.setName(getCronJobName(controlScheduleVO));
    	jobDetail.setGroup(groupName);
    	jobDetail.setJobClass(jobClassFactory.getJobClass(controlScheduleVO.getControlName()));
    	
    	JobDataMap jobParameters = new JobDataMap();
    	jobParameters.put(PARAM_JOB_NAME, controlScheduleVO.getControlName().getLiteral().toLowerCase());
    	jobParameters.put(PARAM_JOB_SCHEDULE, controlScheduleVO.getId());
    	
    	jobDetail.setJobDataMap(jobParameters);
    	
    	return jobDetail;
	}
	
	private CronTrigger getCronTrigger(ControlScheduleVO controlScheduleVO, String groupName, String jobName, String jobGroup) {
		CronTriggerBean trigger = new CronTriggerBean();
		trigger.setName(getCronTriggerName(controlScheduleVO));
		trigger.setGroup(groupName);
		trigger.setJobName(jobName);
		trigger.setJobGroup(jobGroup);
		
		try {
			trigger.setCronExpression(new CronExpression(controlScheduleVO.getCron()));
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		
		return trigger;
	}
	
	private CronTrigger getCronTrigger(ControlScheduleVO controlScheduleVO, String groupName, JobDetail jobDetail) {
		CronTriggerBean trigger = new CronTriggerBean();
		trigger.setName(getCronTriggerName(controlScheduleVO));
		trigger.setGroup(groupName);
		trigger.setJobDetail(jobDetail);
		
		try {
			trigger.setCronExpression(new CronExpression(controlScheduleVO.getCron()));
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		
		return trigger;
	}
	
	public void setControlScheduleService(ControlScheduleService controlScheduleService) {
		this.controlScheduleService = controlScheduleService;
	}
	
	public void setJobClassFactory(JobClassFactory jobClassFactory) {
		this.jobClassFactory = jobClassFactory;
	}
}
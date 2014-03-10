package com.qopuir.taskcontrol.quartz;

import java.util.Date;
import java.util.List;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.qopuir.taskcontrol.entities.ControlScheduleVO;
import com.qopuir.taskcontrol.entities.enums.ControlScheduleStatus;
import com.qopuir.taskcontrol.service.ControlScheduleService;

public class TaskControlJobLauncherDetails extends QuartzJobBean {
	private static final Logger logger = LoggerFactory.getLogger(TaskControlJobLauncherDetails.class);
	
	private JobOperator jobOperator;
	
	private ControlScheduleService controlScheduleService;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getMergedJobDataMap();
		
		JobParameters jobParameters = getJobParametersFromJobDataMap(jobDataMap);
		
		List<ControlScheduleVO> controlSchedulesToRun = controlScheduleService.findReadyToRun();

		for (ControlScheduleVO controlScheduleVO : controlSchedulesToRun) {
			logger.debug("ControlScheduleToRun: {}", controlScheduleVO.toString());
			
			if (controlScheduleVO.getStatus() == ControlScheduleStatus.RUNNING) {
				try {
					jobOperator.getRunningExecutions(getJobName(controlScheduleVO));
					
					// If there are no JobExecution running a NoSuchJobException exception is thrown
					logger.debug("The job {} which has an status of {} is already running", getJobName(controlScheduleVO), controlScheduleVO.getStatus());
				} catch (NoSuchJobException e) {
					logger.debug("The job {} which has an status of {} is not running", getJobName(controlScheduleVO), controlScheduleVO.getStatus());
					
					// TODO (qopuir): schedule job execution
				}
			} else {
				logger.debug("The job {} which has an status of {} is not running", getJobName(controlScheduleVO), controlScheduleVO.getStatus());
				
				// TODO (qopuir): schedule job execution
				
				controlScheduleService.start(controlScheduleVO.getId());
			}
		}
		
		List<ControlScheduleVO> controlSchedulesToFinish = controlScheduleService.findReadyToFinish();

		for (ControlScheduleVO controlScheduleVO : controlSchedulesToFinish) {
			try {
				jobOperator.getRunningExecutions(getJobName(controlScheduleVO));
			
				// If there are no JobExecution running a NoSuchJobException exception is thrown
				// TODO (qopuir): finish job execution
			} catch (NoSuchJobException e) {
				logger.debug("The job {} which has an status of {} is already finished", getJobName(controlScheduleVO), controlScheduleVO.getStatus());
			}
		}
		
		/*
		try {
			logger.info(String.format("Job '%s' is going to be run", jobName));
			
			jobLauncher.run(jobLocator.getJob(jobName), jobParameters);
		} catch (JobExecutionAlreadyRunningException e) {
			e.printStackTrace();
		} catch (JobRestartException e) {
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			e.printStackTrace();
		} catch (NoSuchJobException e) {
			e.printStackTrace();
		}
		*/
	}
	
	private String getJobName(ControlScheduleVO controlScheduleVO) {
		StringBuilder sb = new StringBuilder();
		sb.append(controlScheduleVO.getId()).append("-").append(controlScheduleVO.getControlName().getLiteral());
		
		return sb.toString();
	}
	
	private JobParameters getJobParametersFromJobDataMap(JobDataMap jobDataMap) {
		JobParametersBuilder builder = new JobParametersBuilder();

		/*
		for (Entry<String, Object> entry : jobDataMap.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value instanceof String && !key.equals(JOB_NAME)) {
				builder.addString(key, (String) value);
			} else if (value instanceof Float || value instanceof Double) {
				builder.addDouble(key, ((Number) value).doubleValue());
			} else if (value instanceof Integer || value instanceof Long) {
				builder.addLong(key, ((Number) value).longValue());
			} else if (value instanceof Date) {
				builder.addDate(key, (Date) value);
			} else {
				// JobDataMap contains values which are not job parameters
				// (ignoring)
			}
		}
		*/

		builder.addDate("runDate", new Date());

		return builder.toJobParameters();
	}

	public void setJobOperator(JobOperator jobOperator) {
		this.jobOperator = jobOperator;
	}

	public void setControlScheduleService(ControlScheduleService controlScheduleService) {
		this.controlScheduleService = controlScheduleService;
	}
}
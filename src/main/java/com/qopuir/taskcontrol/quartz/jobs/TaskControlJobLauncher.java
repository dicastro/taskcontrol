package com.qopuir.taskcontrol.quartz.jobs;

import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_JOB_NAME;
import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_JOB_SCHEDULE;
import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_EJECUTION_DATE;

import org.joda.time.LocalDateTime;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class TaskControlJobLauncher extends QuartzJobBean {
	private static final Logger logger = LoggerFactory.getLogger(TaskControlJobLauncher.class);
	
	private JobOperator jobOperator;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.info("Job {} (schedule {}) is being run...", context.getMergedJobDataMap().getString(PARAM_JOB_NAME), context.getMergedJobDataMap().getLong(PARAM_JOB_SCHEDULE));
		
		try {
			jobOperator.start(context.getMergedJobDataMap().getString(PARAM_JOB_NAME), getParameters(context.getMergedJobDataMap()));
		} catch (NoSuchJobException e) {
			logger.error(e.getMessage(), e);
		} catch (JobInstanceAlreadyExistsException e) {
			logger.error(e.getMessage(), e);
		} catch (JobParametersInvalidException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private String getParameters(JobDataMap jobDataMap) {
		StringBuilder sb = new StringBuilder();
		sb.append(PARAM_EJECUTION_DATE).append("=").append(new LocalDateTime().withMillisOfSecond(0));
		sb.append(",").append(PARAM_JOB_NAME).append("=").append(jobDataMap.getString(PARAM_JOB_NAME));
		sb.append(",").append(PARAM_JOB_SCHEDULE).append("=").append(jobDataMap.getLong(PARAM_JOB_SCHEDULE));
		
		return sb.toString();
	}

	public void setJobOperator(JobOperator jobOperator) {
		this.jobOperator = jobOperator;
	}
}
package com.qopuir.taskcontrol.quartz.jobs;

import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_JOB_NAME_TO_RUN;
import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_JOB_NAME;
import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_EJECUTION_DATE;

import org.joda.time.LocalDateTime;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class ProjectionSemanalJob extends QuartzJobBean {
	private static final Logger logger = LoggerFactory.getLogger(ProjectionSemanalJob.class);
	
	private JobOperator jobOperator;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.info("Job {} is being run...", context.getMergedJobDataMap().getString(PARAM_JOB_NAME_TO_RUN));
		
		try {
			jobOperator.start(context.getMergedJobDataMap().getString(PARAM_JOB_NAME_TO_RUN), getParameters(context.getMergedJobDataMap().getString(PARAM_JOB_NAME_TO_RUN)));
		} catch (NoSuchJobException e) {
			logger.error(e.getMessage(), e);
		} catch (JobInstanceAlreadyExistsException e) {
			logger.error(e.getMessage(), e);
		} catch (JobParametersInvalidException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private String getParameters(String jobName) {
		StringBuilder sb = new StringBuilder();
		sb.append(PARAM_EJECUTION_DATE).append("=").append(new LocalDateTime().withMillisOfSecond(0));
		sb.append(",").append(PARAM_JOB_NAME).append("=").append(jobName);
		
		return sb.toString();
	}

	public void setJobOperator(JobOperator jobOperator) {
		this.jobOperator = jobOperator;
	}
}
package com.qopuir.taskcontrol.quartz;

import java.util.Date;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class TaskControlJobLauncherDetails extends QuartzJobBean {
	private static final Logger logger = LoggerFactory.getLogger(TaskControlJobLauncherDetails.class);
	private static final String JOB_NAME_PARAM = "jobName";
	
	private JobLocator jobLocator;
	private JobLauncher jobLauncher;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getMergedJobDataMap();
		
		String jobName = jobDataMap.getString(JOB_NAME_PARAM);
		
		JobParameters jobParameters = getJobParametersFromJobDataMap(jobDataMap);
		
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

	public void setJobLocator(JobLocator jobLocator) {
		this.jobLocator = jobLocator;
	}

	public void setJobLauncher(JobLauncher jobLauncher) {
		this.jobLauncher = jobLauncher;
	}
}
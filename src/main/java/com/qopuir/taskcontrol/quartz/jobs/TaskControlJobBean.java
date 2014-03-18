package com.qopuir.taskcontrol.quartz.jobs;

import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_JOB_EXECUTION_TIME;
import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_JOB_NAME;
import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_JOB_SCHEDULE;

import java.util.Map;

import org.joda.time.LocalDateTime;
import org.springframework.batch.core.JobParameter;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.qopuir.taskcontrol.entities.enums.ControlName;

public abstract class TaskControlJobBean extends QuartzJobBean {
	public void addCommonJobParameters(Map<String, JobParameter> jobParameters, ControlName controlName, Long controlScheduleId, LocalDateTime executionTime) {
		jobParameters.put(PARAM_JOB_NAME, new JobParameter(controlName.getLiteral()));
		jobParameters.put(PARAM_JOB_SCHEDULE, new JobParameter(controlScheduleId));
		jobParameters.put(PARAM_JOB_EXECUTION_TIME, new JobParameter(executionTime.toDate()));
	}
}
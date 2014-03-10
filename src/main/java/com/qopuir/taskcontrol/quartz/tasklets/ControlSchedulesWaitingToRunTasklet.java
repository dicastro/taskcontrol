package com.qopuir.taskcontrol.quartz.tasklets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.qopuir.taskcontrol.service.ControlScheduleService;

public class ControlSchedulesWaitingToRunTasklet implements Tasklet, InitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(ControlSchedulesWaitingToRunTasklet.class);
	
	private ControlScheduleService controlScheduleService;

	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		logger.info(String.format("Running tasklet %s", this.getClass().getSimpleName()));
		
		return RepeatStatus.FINISHED;
	}
	
	public void setControlScheduleService(ControlScheduleService controlScheduleService) {
		this.controlScheduleService = controlScheduleService;
	}
	
	public void afterPropertiesSet() throws Exception {
        Assert.notNull(controlScheduleService, "ControlScheduleService must be set");
    }
}
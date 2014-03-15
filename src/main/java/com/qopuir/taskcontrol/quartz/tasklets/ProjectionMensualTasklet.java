package com.qopuir.taskcontrol.quartz.tasklets;

import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_EJECUTION_DATE;
import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_JOB_NAME;
import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_JOB_SCHEDULE;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.qopuir.taskcontrol.entities.MailMessage;
import com.qopuir.taskcontrol.entities.UserVO;
import com.qopuir.taskcontrol.entities.enums.ControlName;
import com.qopuir.taskcontrol.service.MailService;
import com.qopuir.taskcontrol.service.UserService;

public class ProjectionMensualTasklet implements Tasklet {
	private static final Logger logger = LoggerFactory.getLogger(ProjectionMensualTasklet.class);
	
	private final ControlName controlName = ControlName.PROJECTION_MENSUAL;
	
	@Value("${projectionMensual.simulate}")
	private Boolean simulate;
	@Value("${projectionMensual.subject}")
	private String mailSubject;
	@Value("${projectionMensual.from}")
	private String mailFrom;
	@Value("${projectionMensual.mailTemplate}")
	private String mailTemplate;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	MailService mailService;
	
	@Autowired
	UserService userService;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		logger.info("Job {} (schedule {}) running", getParameter(chunkContext, PARAM_JOB_NAME), getParameter(chunkContext, PARAM_JOB_SCHEDULE));
		
		logger.debug("received parameter {} with value: {}", PARAM_EJECUTION_DATE, getParameter(chunkContext, PARAM_EJECUTION_DATE));
		
		List<UserVO> controlUsers = userService.listControlUsers(controlName);
		
		logger.debug("Users under control {}: {}", controlName, StringUtils.join(controlUsers, ","));
		
		for (UserVO userVO : controlUsers) {
			if (simulate) {
				logger.info("Simulating...");
			} else {
				logger.warn("Sending an email to <{}> at {}", userVO.getEmail(), getParameter(chunkContext, PARAM_EJECUTION_DATE));
				
				mailService.send(new MailMessage().setReceiverEmail(userVO.getEmail()).setSenderEmail(mailFrom).setSubject(mailSubject).setHtmlBody(templateEngine.process(mailTemplate, new Context())));
			}
		}
		
		return RepeatStatus.FINISHED;
	}
	
	private String getParameter(ChunkContext context, String paramName) {
		return context.getStepContext().getStepExecution().getJobParameters().getString(paramName);
	}
}
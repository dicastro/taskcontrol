package com.qopuir.taskcontrol.quartz.tasklets;

import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_MAIL_CONTEXT;
import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_MAIL_FROM;
import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_MAIL_SUBJECT;
import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_MAIL_TEMPLATE;
import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_MAIL_TO;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qopuir.taskcontrol.entities.MailMessage;
import com.qopuir.taskcontrol.quartz.jobs.rest.SaisieErrorsRestClientJobDataErrorsResponse;
import com.qopuir.taskcontrol.service.MailService;

public class SendMailTasklet implements Tasklet {
	private static final Logger logger = LoggerFactory.getLogger(SendMailTasklet.class);

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	MailService mailService;

	@Autowired
	ObjectMapper objectMapper;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		logger.info("Sending email from {} to {} with subject '{}' and template '{}'",
				getParameter(chunkContext, PARAM_MAIL_FROM), getParameter(chunkContext, PARAM_MAIL_TO),
				getParameter(chunkContext, PARAM_MAIL_SUBJECT), getParameter(chunkContext, PARAM_MAIL_TEMPLATE));

		Map<String, Object> context = new HashMap<String, Object>();
		context.put(PARAM_MAIL_CONTEXT, objectMapper.readValue(getParameter(chunkContext, PARAM_MAIL_CONTEXT),
				new TypeReference<List<SaisieErrorsRestClientJobDataErrorsResponse>>() {}));

		mailService.send(new MailMessage()
				.setReceiverEmail(getParameter(chunkContext, PARAM_MAIL_TO))
				.setSenderEmail(getParameter(chunkContext, PARAM_MAIL_FROM))
				.setSubject(getParameter(chunkContext, PARAM_MAIL_SUBJECT))
				.setHtmlBody(
						templateEngine.process(getParameter(chunkContext, PARAM_MAIL_TEMPLATE), new Context(
								Locale.ENGLISH, context))));

		return RepeatStatus.FINISHED;
	}

	private String getParameter(ChunkContext context, String paramName) {
		return context.getStepContext().getStepExecution().getJobParameters().getString(paramName);
	}
}
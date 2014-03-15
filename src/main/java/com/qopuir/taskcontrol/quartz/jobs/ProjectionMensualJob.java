package com.qopuir.taskcontrol.quartz.jobs;

import static com.qopuir.taskcontrol.quartz.constants.JobConstants.JOB_SEND_MAIL;
import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_JOB_NAME;
import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_JOB_SCHEDULE;
import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_JOB_EXECUTION_TIME;
import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_MAIL_FROM;
import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_MAIL_SUBJECT;
import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_MAIL_TEMPLATE;
import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_MAIL_TO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.qopuir.taskcontrol.entities.UserVO;
import com.qopuir.taskcontrol.entities.enums.ControlName;
import com.qopuir.taskcontrol.service.UserService;

public class ProjectionMensualJob extends QuartzJobBean {
	private static final Logger logger = LoggerFactory.getLogger(ProjectionMensualJob.class);
	
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
	private JobLauncher jobLauncher;
	
	@Autowired
	private JobRegistry jobRegistry;
	
	@Autowired
	UserService userService;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		LocalDateTime executionTime = new LocalDateTime().withMillisOfSecond(0);
		
		logger.info("Job {} (schedule {}) running at {}", context.getMergedJobDataMap().getString(PARAM_JOB_NAME), context.getMergedJobDataMap().getLong(PARAM_JOB_SCHEDULE), executionTime);
		
		
		List<UserVO> controlUsers = userService.listControlUsers(controlName);
		
		logger.debug("Users under control {}: {}", controlName, StringUtils.join(controlUsers, ","));
		
		for (UserVO userVO : controlUsers) {
			if (simulate) {
				logger.info("Simulating...");
				
				logger.debug("Email would be sent from {} to {} with subject '{}' and template '{}'", mailFrom, userVO.getEmail(), mailSubject, mailTemplate);
			} else {
				logger.warn("Sending an email to <{}> at {}", userVO.getEmail(), executionTime);
				
				try {
					Map<String, JobParameter> jobParameters = getMailParameters(mailFrom, userVO.getEmail(), mailSubject, mailTemplate);
					jobParameters.put(PARAM_JOB_EXECUTION_TIME, new JobParameter(executionTime.toDate()));
					
					jobLauncher.run(jobRegistry.getJob(JOB_SEND_MAIL), new JobParameters(jobParameters));
				} catch (JobExecutionAlreadyRunningException e) {
					logger.error(e.getMessage(), e);
				} catch (JobRestartException e) {
					logger.error(e.getMessage(), e);
				} catch (JobInstanceAlreadyCompleteException e) {
					logger.error(e.getMessage(), e);
				} catch (JobParametersInvalidException e) {
					logger.error(e.getMessage(), e);
				} catch (NoSuchJobException e) {
					logger.error(e.getMessage(), e);
				}
				
				logger.debug("Sent email from {} to {} with subject '{}' and template '{}'", mailFrom, userVO.getEmail(), mailSubject, mailTemplate);
			}
		}
	}
	
	private Map<String, JobParameter> getMailParameters(String from, String to, String subject, String template) {
		Map<String, JobParameter> parametersMap = new HashMap<String, JobParameter>();
		parametersMap.put(PARAM_MAIL_FROM, new JobParameter(from));
		parametersMap.put(PARAM_MAIL_TO, new JobParameter(to));
		parametersMap.put(PARAM_MAIL_SUBJECT, new JobParameter(subject));
		parametersMap.put(PARAM_MAIL_TEMPLATE, new JobParameter(template));
		
		return parametersMap;
	}
}
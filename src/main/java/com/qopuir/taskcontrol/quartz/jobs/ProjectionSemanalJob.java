package com.qopuir.taskcontrol.quartz.jobs;

import static com.qopuir.taskcontrol.quartz.constants.JobConstants.JOB_SEND_MAIL;
import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_JOB_EXECUTION_TIME;
import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_JOB_NAME;
import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_JOB_SCHEDULE;
import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_MAIL_FROM;
import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_MAIL_SUBJECT;
import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_MAIL_TEMPLATE;
import static com.qopuir.taskcontrol.quartz.constants.JobConstants.PARAM_MAIL_TO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;
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
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.qopuir.taskcontrol.entities.ControlScheduleParamVO;
import com.qopuir.taskcontrol.entities.UserVO;
import com.qopuir.taskcontrol.entities.enums.ControlName;
import com.qopuir.taskcontrol.entities.enums.ParamName;
import com.qopuir.taskcontrol.service.ControlScheduleService;
import com.qopuir.taskcontrol.service.UserService;

public class ProjectionSemanalJob extends QuartzJobBean {
	private static final Logger logger = LoggerFactory.getLogger(ProjectionSemanalJob.class);
	
	private final ControlName controlName = ControlName.PROJECTION_SEMANAL;
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private JobRegistry jobRegistry;
	
	@Autowired
	UserService userService;
	@Autowired
	ControlScheduleService controlScheduleService;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		LocalDateTime executionTime = new LocalDateTime().withMillisOfSecond(0);
		
		Long controlScheduleId = context.getMergedJobDataMap().getLong(PARAM_JOB_SCHEDULE);
		
		logger.info("Job {} (schedule {}) running at {}", context.getMergedJobDataMap().getString(PARAM_JOB_NAME), controlScheduleId, executionTime);
		
		Map<ParamName, ControlScheduleParamVO> controlScheduleParams = controlScheduleService.getControlParams(controlName, controlScheduleId);
		
		List<UserVO> controlUsers = userService.listControlUsers(controlName);
		
		logger.debug("Users under control {}: {}", controlName, StringUtils.join(controlUsers, ","));
		
		for (UserVO userVO : controlUsers) {
			logger.debug("Checking projection of user {}", userVO.getUsername());
			
			if (Boolean.parseBoolean(controlScheduleParams.get(ParamName.SIMULATE).getValue())) {
				logger.info("Simulating...");
				
				logger.debug("Email would be sent from {} to {} with subject '{}' and template '{}'", controlScheduleParams.get(ParamName.MAIL_FROM).getValue(), userVO.getEmail(), controlScheduleParams.get(ParamName.MAIL_SUBJECT).getValue(), controlScheduleParams.get(ParamName.MAIL_TEMPLATE).getValue());
			} else {
				if (checkProjectionIncompleteUntilToday(controlScheduleParams.get(ParamName.URL).getValue(), userVO)) {
					logger.warn("User <{}> has projection incomplete at {}. Sending an email to <{}>", userVO.getUsername(), executionTime, userVO.getEmail());
					
					try {
						Map<String, JobParameter> jobParameters = getMailParameters(controlScheduleParams.get(ParamName.MAIL_FROM).getValue(), userVO.getEmail(), controlScheduleParams.get(ParamName.MAIL_SUBJECT).getValue(), controlScheduleParams.get(ParamName.MAIL_TEMPLATE).getValue());
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
					
					logger.debug("Sent email from {} to {} with subject '{}' and template '{}'", controlScheduleParams.get(ParamName.MAIL_FROM).getValue(), userVO.getEmail(), controlScheduleParams.get(ParamName.MAIL_SUBJECT).getValue(), controlScheduleParams.get(ParamName.MAIL_TEMPLATE).getValue());
				}
			}
		}
	}
	
	private boolean checkProjectionIncompleteUntilToday(String url, UserVO userVO) {
		WebDriver driver = new HtmlUnitDriver();
        driver.get(url);

        Select userField = new Select(driver.findElement(By.cssSelector("select#form_auth_name")));
        userField.selectByVisibleText(userVO.getUsername());

        WebElement element = driver.findElement(By.cssSelector("input#form_auth_password"));
        element.sendKeys(userVO.getPassword());
        
        element.submit();

        driver.get(url + "/?page=home");
        
        List<WebElement> incompleteDays = driver.findElements(By.cssSelector("td.calendar-day p.day-number-red"));

        driver.quit();
        
        return incompleteDays.size() > 0;
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
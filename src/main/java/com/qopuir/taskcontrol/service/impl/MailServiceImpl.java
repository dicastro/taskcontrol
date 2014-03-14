package com.qopuir.taskcontrol.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.qopuir.taskcontrol.entities.MailMessage;
import com.qopuir.taskcontrol.sendgrid.SendGridParameters;
import com.qopuir.taskcontrol.service.MailService;

@Service
public class MailServiceImpl implements MailService {
	protected static Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
	private RestTemplate restTemplate = new RestTemplate();
	
	@Value("${sendgrid.username}")
	private String sendgridUsername;
	
	@Value("${sendgrid.apiKey}")
	private String sendgridApiKey;
	
	@Override
	public void send(MailMessage message) {
		MultiValueMap<String, Object> vars = new LinkedMultiValueMap<String, Object>();
		vars.add(SendGridParameters.API_USER, sendgridUsername);
		vars.add(SendGridParameters.API_KEY, sendgridApiKey);
		
		if (message.getSenderName() != null) {
			vars.add(SendGridParameters.SENDER_NAME, message.getSenderName());
		}
		
		if (message.getSenderEmail() != null) {
			vars.add(SendGridParameters.SENDER_EMAIL, message.getSenderEmail());
		}
		
		if (message.getCcEmail() != null) {
			vars.add(SendGridParameters.BLIND_COPY_EMAIL, message.getCcEmail());
		}
		
		if (message.getSubject() != null) {
			vars.add(SendGridParameters.SUBJECT, message.getSubject());
		}
		
		vars.add(SendGridParameters.TEXT, "");
		
		if (message.getHtmlBody() != null) {
			vars.add(SendGridParameters.HTML, message.getHtmlBody());
		}
		
		if (message.getReceiverEmail() != null) {
			vars.add(SendGridParameters.RECEIVER_EMAIL, message.getReceiverEmail());
		}
		
		if (message.getReceiverName() != null) {
			vars.add(SendGridParameters.RECEIVER_NAME, message.getReceiverName());
		}
		
		restTemplate.postForLocation(SendGridParameters.URL, vars);
	}
}
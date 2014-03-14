package com.qopuir.taskcontrol.service.impl;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.qopuir.taskcontrol.entities.MailMessage;
import com.qopuir.taskcontrol.service.MailService;

@Service
public class MailServiceImpl implements MailService {
	protected static Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
	
	@Value("${mail.host}")
	private String mailHost;
	
	@Value("${mail.port}")
	private String mailPort;
	
	@Value("${mail.username}")
	private String mailUsername;
	
	@Value("${mail.password}")
	private String mailPassword;
	
	@Override
	public void send(MailMessage mail) {
		Properties props = new Properties();
		props.put("mail.smtp.host", mailHost);
		props.put("mail.smtp.socketFactory.port", mailPort);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", mailPort);
 
		Session session = Session.getDefaultInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailUsername, mailPassword);
			}
		});
 
		try {
			Message message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress(mail.getSenderEmail()));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getReceiverEmail()));
			message.setSubject(mail.getSubject());
			message.setText(mail.getHtmlBody());
 
			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
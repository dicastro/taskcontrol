package com.qopuir.taskcontrol.entities;

import java.io.Serializable;

public class MailMessage implements Serializable {
	private static final long serialVersionUID = -6183817211427158444L;

	private String senderName;
	private String senderEmail;
	private String ccEmail;
	private String subject;
	private String htmlBody;
	private String receiverName;
	private String receiverEmail;
	
	public MailMessage() {
		super();
	}

	public String getSenderName() {
		return senderName;
	}

	public MailMessage setSenderName(String senderName) {
		this.senderName = senderName;
		return this;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public MailMessage setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
		return this;
	}

	public String getCcEmail() {
		return ccEmail;
	}

	public MailMessage setCcEmail(String ccEmail) {
		this.ccEmail = ccEmail;
		return this;
	}

	public String getSubject() {
		return subject;
	}

	public MailMessage setSubject(String subject) {
		this.subject = subject;
		return this;
	}

	public String getHtmlBody() {
		return htmlBody;
	}

	public MailMessage setHtmlBody(String htmlBody) {
		this.htmlBody = htmlBody;
		return this;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public MailMessage setReceiverName(String receiverName) {
		this.receiverName = receiverName;
		return this;
	}

	public String getReceiverEmail() {
		return receiverEmail;
	}

	public MailMessage setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
		return this;
	}
}
package com.qopuir.taskcontrol.service;

import com.qopuir.taskcontrol.entities.MailMessage;

public interface MailService {
	void send(MailMessage message);
}
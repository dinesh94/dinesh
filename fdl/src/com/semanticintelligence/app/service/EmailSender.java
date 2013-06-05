package com.semanticintelligence.app.service;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {

	protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "mailSender")
	private MailSender mailSender;

	public MailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

}

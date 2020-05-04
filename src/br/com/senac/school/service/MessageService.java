package br.com.senac.school.service;

import br.com.senac.school.email.EmailMessage;

public class MessageService {

	private MessageService() {
	
	}

	public static EmailMessage registration(String receiver, int token) {

		String subject = "subject";
		String body = "body";

		return new EmailMessage().generate(receiver, token, subject, body);
	}

	public static EmailMessage resendToken(String receiver, int token) {

		String subject = "subjectResendToken";
		String body = "resendToken";

		return new EmailMessage().generate(receiver, token, subject, body);

	}

}

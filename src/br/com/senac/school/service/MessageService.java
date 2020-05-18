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

	public static EmailMessage resetPassword(String receiver, int token) {

		String subject = "subjectResetPassword";
		String body = "resetPassword";

		return new EmailMessage().generate(receiver, token, subject, body);
	}

}

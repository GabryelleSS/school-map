package br.com.senac.school.email;

import java.util.Properties;

import br.com.senac.school.util.LoadProperties;

public class EmailMessage {

	private final String PROPERTIES_PATH = "email.properties";

	private String receiver;
	private String subject;
	private String body;

	public String getReceiver() {
		return receiver;
	}

	public String getSubject() {
		return subject;
	}

	public String getBody() {
		return body;
	}

	public EmailMessage generate(String receiver, int token, String subject, String body) {
		Properties properties = new LoadProperties().load(PROPERTIES_PATH);

		subject = properties.getProperty(subject);
		body = properties.getProperty(body).concat(" ").concat(String.valueOf(token));

		EmailMessage message = new EmailMessage();
		message.receiver = receiver;
		message.body = body;
		message.subject = subject;

		return message;
	}
}

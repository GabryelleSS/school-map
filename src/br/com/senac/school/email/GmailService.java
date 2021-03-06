package br.com.senac.school.email;

import java.util.Properties;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;

import br.com.senac.school.util.LoadProperties;

public class GmailService implements EmailSender {

	private final String PROPERTIE_EMAIL = "/br/com/senac/properties/email.properties";
	private final String PROPERTIE_GMAIL = "/br/com/senac/properties/gmail.properties";

	private SimpleEmail simpleEmail;
	private String hostName;
	private int port;

	private String from;
	private String email;
	private String password;

	public GmailService() {
		loadProperties();
		config();
	}

	private void loadProperties() {
		Properties emailProperties = new LoadProperties().load(PROPERTIE_EMAIL);
		Properties gmailProperties = new LoadProperties().load(PROPERTIE_GMAIL);

		email = emailProperties.getProperty("email");
		password = emailProperties.getProperty("password");
		from = emailProperties.getProperty("from");

		hostName = gmailProperties.getProperty("hostName");
		port = Integer.valueOf(gmailProperties.getProperty("port"));
	}

	private void config() {
		simpleEmail = new SimpleEmail();
		simpleEmail.setHostName(hostName);
		simpleEmail.setSmtpPort(port);
		simpleEmail.setAuthenticator(new DefaultAuthenticator(email, password));
		simpleEmail.setSSLOnConnect(true);
	}

	@Override
	public void send(EmailMessage message) {
		Runnable emailSendTask = () -> {
			try {
				simpleEmail.setFrom(email, from);
				simpleEmail.setSubject(message.getSubject());
				simpleEmail.setMsg(message.getBody());
				simpleEmail.addTo(message.getReceiver());
				simpleEmail.buildMimeMessage();
				simpleEmail.sendMimeMessage();
			} catch (Exception e) {

			}

		};
		Thread mailSender = new Thread(emailSendTask, "EMAIL-SENDER");
		mailSender.start();
	}

}

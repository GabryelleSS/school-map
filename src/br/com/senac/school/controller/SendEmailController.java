package br.com.senac.school.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import br.com.senac.school.dao.UsuarioDao;
import br.com.senac.school.dao.UsuarioDaoImpl;
import br.com.senac.school.email.EmailSender;
import br.com.senac.school.email.GmailService;
import br.com.senac.school.email.Token;
import br.com.senac.school.model.Usuario;
import br.com.senac.school.service.MessageService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class SendEmailController implements Initializable {

	@FXML
	private JFXTextField fieldToken;

	@FXML
	private AnchorPane root;

	private EmailSender service;
	public static Usuario usuario;
	private int token;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		service = new GmailService();
		sendEmail();
	}

	private void sendEmail() {
		token = Token.generate();
		service.send(MessageService.registration(usuario.getEmail(), token));
	}

	private void sendEmailResendToken() {
		service = new GmailService();
		token = Token.generate();
		service.send(MessageService.resendToken(usuario.getEmail(), token));
	}

	@FXML
	void btnRegistration(ActionEvent event) {
		String token = fieldToken.getText();

		if (token.equals(String.valueOf(this.token))) {
			persistUsuario(usuario);
			loadDashboard();

		} else {

			// TODO colocar alerta de token invalido
		}
	}

	@FXML
	void btnResendToken(ActionEvent event) {
		sendEmailResendToken();
	}

	private void loadDashboard() {

		try {
			Parent pane = FXMLLoader.load(getClass().getResource("/br/com/senac/school/view/Dashboard.fxml"));
			root.getChildren().clear();
			root.getChildren().add(pane);
		} catch (IOException e) {
		}
	}

	private void persistUsuario(Usuario usuario) {
		UsuarioDao dao = new UsuarioDaoImpl();
		dao.save(usuario);
	}

}

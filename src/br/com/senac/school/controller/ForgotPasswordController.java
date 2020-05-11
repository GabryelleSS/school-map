package br.com.senac.school.controller;

import java.util.List;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import br.com.senac.school.dao.UsuarioDao;
import br.com.senac.school.dao.UsuarioDaoImpl;
import br.com.senac.school.email.EmailSender;
import br.com.senac.school.email.GmailService;
import br.com.senac.school.email.Token;
import br.com.senac.school.service.MessageService;
import br.com.senac.school.util.Encrypt;
import br.com.senac.school.util.VIEWS_NAMES;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class ForgotPasswordController {

	@FXML
	private AnchorPane root;

	@FXML
	private JFXTextField fieldEmail;

	@FXML
	private JFXTextField fieldToken;

	@FXML
	private JFXPasswordField fieldPassword;

	@FXML
	private JFXPasswordField fieldConfirmPassword;

	private static int token = 0;
	private static String email;
	private UsuarioDao dao;

	@FXML
	void btnConfirmResetToken(ActionEvent event) {
		String password = fieldPassword.getText();
		String confirm = fieldConfirmPassword.getText();

		if (password.equals(confirm)) {
			resetPasswod(email, password);
			email = null;
			loadView(VIEWS_NAMES.LOGIN);
		}
	}

	@FXML
	void btnSend(ActionEvent event) {
		email = fieldEmail.getText();

		boolean verifyEmail = verifyEmail(email);

		if (!email.isEmpty() && verifyEmail) {
			sendEmailReset(email);
			loadView(VIEWS_NAMES.FORGOT_PASSWORD_TOKEN);
		} else {
			// TODO fazer alerta sobre email não cadastrado
		}
	}

	@FXML
	void btnConfirmToken(ActionEvent event) {
		String tokenUser = fieldToken.getText();
		String tokenEmail = String.valueOf(token);

		if (tokenEmail.equals(tokenUser)) {
			loadView(VIEWS_NAMES.FORGOT_PASSWORD_RESET_TOKEN);
		} else {
			// TODO fazer alerta sobre token iválido
		}
	}

	@FXML
	public void btnBackLogin(ActionEvent event) {
		loadView(VIEWS_NAMES.LOGIN);
	}

	public void loadView(VIEWS_NAMES view) {
		try {
			Parent pane = FXMLLoader.load(getClass().getResource(view.getName()));

			root.getChildren().clear();
			root.getChildren().add(pane);

		} catch (Exception e) {

		}
	}

	public boolean verifyEmail(String email) {
		if (dao == null) {
			dao = new UsuarioDaoImpl();
		}
		List<String> list = dao.passwordLogin(email);
		return !list.isEmpty();
	}

	private void resetPasswod(String email, String password) {
		if (dao == null) {
			dao = new UsuarioDaoImpl();
		}
		password = Encrypt.encode(password);
		dao.resetPassword(email, password);
	}

	private void sendEmailReset(String email) {
		token = Token.generate();
		EmailSender service = new GmailService();
		service.send(MessageService.resetPassword(email, token));
	}
}

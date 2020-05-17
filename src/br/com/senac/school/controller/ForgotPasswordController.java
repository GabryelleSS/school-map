package br.com.senac.school.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import br.com.senac.school.dao.UsuarioDao;
import br.com.senac.school.dao.UsuarioDaoImpl;
import br.com.senac.school.email.EmailSender;
import br.com.senac.school.email.GmailService;
import br.com.senac.school.email.Token;
import br.com.senac.school.service.MessageService;
import br.com.senac.school.util.Alert;
import br.com.senac.school.util.Encrypt;
import br.com.senac.school.util.LoadViews;
import br.com.senac.school.util.VIEWS_NAMES;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class ForgotPasswordController {

	@FXML
	private StackPane root1;
	
	@FXML
	private StackPane root2;
	
	@FXML
	private StackPane root3;

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
			loadView(VIEWS_NAMES.LOGIN,root3);

			Alert.show("Senha alterada", "Sua senha foi alterada com sucesso", root3);

		}
	}

	@FXML
	void btnSend(ActionEvent event) {
		email = fieldEmail.getText();

		if (!email.isEmpty() && verifyEmail(email)) {
			sendEmailReset(email);
			loadView(VIEWS_NAMES.FORGOT_PASSWORD_TOKEN, root1);

		} else {

			Alert.show("E-mail não cadastrado",
					"Parece que você ainda não possui um cadastro conosco faça já, é simples e rapido.", root1);
		}

	}

	@FXML
	void btnConfirmToken(ActionEvent event) {
		String tokenUser = fieldToken.getText();
		String tokenEmail = String.valueOf(token);

		if (tokenEmail.equals(tokenUser)) {
			loadView(VIEWS_NAMES.FORGOT_PASSWORD_RESET_TOKEN,root2);
		} else {
			Alert.show("Token inválido", "O token está inválido, por favor insira o token enviado para o seu e-mail.",
					root2);

		}
	}

	@FXML
	public void btnBackLoginRoot1(ActionEvent event) {
		loadView(VIEWS_NAMES.LOGIN, root1);
	}
	@FXML
	public void btnBackLoginRoot2(ActionEvent event) {
		loadView(VIEWS_NAMES.FORGOT_PASSWORD, root2);
	}
	@FXML
	public void btnBackLoginRoot3(ActionEvent event) {
		loadView(VIEWS_NAMES.FORGOT_PASSWORD, root3);
	}

	public void loadView(VIEWS_NAMES view, StackPane pane) {
		new LoadViews().load(pane, view);
	}

	public boolean verifyEmail(String email) {
		if (dao == null) {
			dao = new UsuarioDaoImpl();
		}
		return dao.verify(email);
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

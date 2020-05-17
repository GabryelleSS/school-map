package br.com.senac.school.controller;

import java.io.IOException;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import br.com.senac.school.dao.UsuarioDao;
import br.com.senac.school.dao.UsuarioDaoImpl;
import br.com.senac.school.model.Usuario;
import br.com.senac.school.session.Session;
import br.com.senac.school.util.Alert;
import br.com.senac.school.util.Encrypt;
import br.com.senac.school.util.LoadViews;
import br.com.senac.school.util.VIEWS_NAMES;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class Login {
	@FXML
	private StackPane root;

	@FXML
	private JFXButton login;

	@FXML
	private JFXButton btnUserRegister;

	@FXML
	private JFXTextField fieldEmail;

	@FXML
	private JFXPasswordField fieldPassword;

	@FXML
	void btnLogin(ActionEvent event) throws IOException {
		String email = fieldEmail.getText();
		String password = fieldPassword.getText();

		UsuarioDao dao = new UsuarioDaoImpl();
		List<Usuario> list = dao.findByEmail(email);

		if (!list.isEmpty()) {
			boolean verify = Encrypt.verify(password, list.get(0).getSenha());
			if (verify) {
				Session.setUsuario(list.get(0));
				loadDashboard();
			}
		} else {
			Alert.show("E-mail não cadastrado",
					"Parece que você ainda não possui um cadastro conosco, faça já, é simples e rapido.", root);
		}

	}

	private void loadDashboard() {
		new LoadViews().load(root, VIEWS_NAMES.DASHBOARD);
	}

	@FXML
	private void btnUserRegister() throws Exception {
		new LoadViews().load(root, VIEWS_NAMES.USER_REGISTER);
	}

	@FXML
	public void btnForgotPassword(ActionEvent event) throws IOException {
		new LoadViews().load(root, VIEWS_NAMES.FORGOT_PASSWORD);
	}

}

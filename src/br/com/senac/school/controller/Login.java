package br.com.senac.school.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import br.com.senac.school.dao.UsuarioDao;
import br.com.senac.school.dao.UsuarioDaoImpl;
import br.com.senac.school.util.Encrypt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class Login {
	@FXML
	private AnchorPane root;

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
		List<String> userPassword = dao.passwordLogin(email);

		if (!userPassword.isEmpty()) {
			boolean verify = Encrypt.verify(password, userPassword.get(0));
			if (verify) {
				loadDashboard();
			}
		}
	}

	private void loadDashboard() {
		try {
			Parent pane = FXMLLoader.load(getClass().getResource("/br/com/senac/school/view/Dashboard.fxml"));
			root.getChildren().clear();
			root.getChildren().add(pane);

		} catch (IOException e) {
		}

	}

	@FXML
	private void btnUserRegister() throws Exception {
		Parent userRegister = FXMLLoader.load(getClass().getResource("/br/com/senac/school/view/UserRegister.fxml"));

		root.getChildren().clear();
		root.getChildren().add(userRegister);
	}

	@FXML
	public void btnForgotPassword(ActionEvent event) throws IOException {
		Parent pane = FXMLLoader.load(getClass().getResource("/br/com/senac/school/view/ForgotPassword.fxml"));

		root.getChildren().clear();
		root.getChildren().add(pane);

	}

}

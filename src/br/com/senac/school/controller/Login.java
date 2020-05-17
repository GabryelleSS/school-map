package br.com.senac.school.controller;

import java.io.IOException;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import br.com.senac.school.dao.UsuarioDao;
import br.com.senac.school.dao.UsuarioDaoImpl;
import br.com.senac.school.model.Usuario;
import br.com.senac.school.session.Session;
import br.com.senac.school.util.Encrypt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

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
		List<Usuario> list = dao.findByEmail(email);

		if (!list.isEmpty()) {
			boolean verify = Encrypt.verify(password, list.get(0).getSenha());
			if (verify) {
				Session.setUsuario(list.get(0));
				loadDashboard();
			}
		} else {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("OPS");
			alert.setHeaderText("E-mail não cadastrado");
			alert.setContentText("Parece que você ainda não possui um cadastro conosco faça já, é simples e rapido.");
			alert.show();

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

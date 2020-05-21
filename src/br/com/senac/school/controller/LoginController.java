package br.com.senac.school.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;

import br.com.senac.school.dao.UsuarioDao;
import br.com.senac.school.dao.UsuarioDaoImpl;
import br.com.senac.school.model.Usuario;
import br.com.senac.school.session.Session;
import br.com.senac.school.util.Alert;
import br.com.senac.school.util.Encrypt;
import br.com.senac.school.util.LoadViews;
import br.com.senac.school.util.VIEWS_NAMES;
import br.com.senac.school.util.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class LoginController implements Initializable {
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

	private ValidatorBase validator;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		fieldRequired();
	}

	@FXML
	void btnLogin(ActionEvent event) throws IOException {

		if (checkRequiredFields()) {

			Alert.show("Campos obrigatórios", "Ops! Você precisa preencher os campos obrigatórios.", root);

		} else {
			
			String email = fieldEmail.getText();
			String password = fieldPassword.getText();

			UsuarioDao dao = new UsuarioDaoImpl();
			List<Usuario> list = dao.findByEmail(email);

			if (!list.isEmpty()) {
				boolean verify = Encrypt.verify(password, list.get(0).getSenha());
				if (verify) {
					Session.setUsuario(list.get(0));
					loadDashboard();
				} else {
					Alert.show("Senha inválida", "Ops! A senha está incorreta!", root);
				}
			} else {
				Alert.show("E-mail não cadastrado",
						"Parece que você ainda não possui um cadastro conosco, faça já, é simples e rapido.", root);
			}

		}

	}

	@FXML
	void btnBackHome(MouseEvent event) {
		new LoadViews().load(root, VIEWS_NAMES.HOME);
	}

	private void loadDashboard() {
		new LoadViews().load(root, VIEWS_NAMES.DASHBOARD);
	}

	@FXML
	private void btnUserRegister(MouseEvent event) throws Exception {
		new LoadViews().load(root, VIEWS_NAMES.USER_REGISTER);
	}

	@FXML
	public void btnForgotPassword(MouseEvent event) throws IOException {
		new LoadViews().load(root, VIEWS_NAMES.FORGOT_PASSWORD);
	}

	private void fieldRequired() {
		validator = new RequiredFieldValidator();
		validator.setMessage("Campo obrigatório!");
		Validator.validate(validator, fieldEmail, fieldPassword);
	}

	private boolean checkRequiredFields() {

		List<Boolean> list = List.of(fieldEmail.getText().trim().isEmpty(), fieldPassword.getText().trim().isEmpty());

		for (Boolean var : list) {
			if (var) {
				return true;
			}
		}
		return false;
	}
}

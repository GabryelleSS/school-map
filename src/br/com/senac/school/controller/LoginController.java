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
import br.com.senac.school.log.Logs;
import br.com.senac.school.model.Usuario;
import br.com.senac.school.session.Session;
import br.com.senac.school.util.Alert;
import br.com.senac.school.util.Encrypt;
import br.com.senac.school.util.LoadViews;
import br.com.senac.school.util.VIEWS_NAMES;
import br.com.senac.school.util.Validator;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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

	@FXML
	private Label labelForgotPassword;

	@FXML
	private ImageView spinner;

	private ValidatorBase validator;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		fieldRequired();
	}

	@FXML
	void btnLogin(ActionEvent actionEvent) throws IOException {
		labelForgotPassword.setVisible(false);
		spinner.setVisible(true);

		if (checkRequiredFields()) {
			labelForgotPassword.setVisible(true);
			spinner.setVisible(false);

			Alert.show("Campos obrigat�rios", "Ops! Voc� precisa preencher os campos obrigat�rios.", root);

		} else {

			String email = fieldEmail.getText();
			String password = fieldPassword.getText();

			Service<List<Usuario>> searchUsuario = new Service<List<Usuario>>() {

				@Override
				protected Task<List<Usuario>> createTask() {
					return new Task<List<Usuario>>() {

						protected List<Usuario> call() throws Exception {
							UsuarioDao dao = new UsuarioDaoImpl();
							List<Usuario> list = dao.findByEmail(email);
							return list;
						}
					};
				}

				protected void succeeded() {
					reset();
				};
			};

			searchUsuario.start();

			searchUsuario.setOnSucceeded((event) -> {

				List<Usuario> list = searchUsuario.getValue();

				if (!list.isEmpty()) {
					boolean verify = Encrypt.verify(password, list.get(0).getSenha());
					if (verify) {
						Logs.info("LOGIN");
						Session.setUsuario(list.get(0));
						loadDashboard();
					} else {
						labelForgotPassword.setVisible(true);
						spinner.setVisible(false);
						Alert.show("Senha inv�lida", "Ops! A senha est� incorreta!", root);
					}
				} else {
					labelForgotPassword.setVisible(true);
					spinner.setVisible(false);
					Alert.show("E-mail n�o cadastrado",
							"Parece que voc� ainda n�o possui um cadastro conosco, fa�a j�, � simples e rapido.", root);
				}

			});
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
		validator.setMessage("Campo obrigat�rio!");
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

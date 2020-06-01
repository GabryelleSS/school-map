package br.com.senac.school.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.base.IFXValidatableControl;
import com.jfoenix.validation.RequiredFieldValidator;

import br.com.senac.school.dao.UsuarioDao;
import br.com.senac.school.dao.UsuarioDaoImpl;
import br.com.senac.school.email.EmailSender;
import br.com.senac.school.email.GmailService;
import br.com.senac.school.email.Token;
import br.com.senac.school.log.Logs;
import br.com.senac.school.service.MessageService;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class ForgotPasswordController implements Initializable {

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

	@FXML
	private ImageView spinner;

	@FXML
	private Text labelText;

	private static int token = 0;
	private static String email;
	private UsuarioDao dao;

	private RequiredFieldValidator validator;

	private static int validateFields = 1;

	@FXML
	void btnConfirmResetToken(ActionEvent event) {

		boolean pass = fieldPassword.getText().trim().isEmpty();
		boolean conf = fieldConfirmPassword.getText().trim().isEmpty();

		if (pass || conf) {
			alertFieldRequired(root3);

		} else if (!fieldPassword.getText().equals(fieldConfirmPassword.getText())) {

			Alert.show("Senha incorreta", "Ops! As senhas estão diferentes!", root3);

		} else {
			String password = fieldPassword.getText();
			String confirm = fieldConfirmPassword.getText();

			if (password.equals(confirm)) {
				Logs.info("Redefinição de senha");

				resetPasswod(email, password);

				email = null;
				validateFields = 1;
				loadView(VIEWS_NAMES.LOGIN, root3);

				Alert.show("Senha alterada", "Sua senha foi alterada com sucesso", root3);

			}
		}
	}

	Service<Boolean> taskVefifyEmail = new Service<Boolean>() {

		@Override
		protected Task<Boolean> createTask() {
			return new Task<Boolean>() {
				@Override
				protected Boolean call() throws Exception {
					if (dao == null) {
						dao = new UsuarioDaoImpl();
					}

					return dao.verify(fieldEmail.getText());

				}
			};
		}

		protected void succeeded() {
			reset();
		};
	};

	@FXML
	void btnSend(ActionEvent actionEvent) {

		if (checkRequiredFields(List.of(fieldEmail))) {
			alertFieldRequired(root1);
		} else {
			spinner.setVisible(true);
			labelText.setOpacity(0.39);
			fieldEmail.setOpacity(0.39);
			email = fieldEmail.getText();
			taskVefifyEmail.start();

			taskVefifyEmail.setOnSucceeded((event) -> {

				if (taskVefifyEmail.getValue()) {
					validateFields++;
					sendEmailReset(email);
					spinner.setVisible(false);
					loadView(VIEWS_NAMES.FORGOT_PASSWORD_TOKEN, root1);
				} else {
					spinner.setVisible(false);
					labelText.setOpacity(1);
					fieldEmail.setOpacity(1);
					Alert.show("E-mail não cadastrado",
							"Parece que você ainda não possui um cadastro conosco faça já, é simples e rapido.", root1);
				}
			});
		}
	}

	private void alertFieldRequired(StackPane pane) {
		Alert.show("Campos obrigatórios", "Ops! Você precisa preencher os campos obrigatórios.", pane);
	}

	@FXML
	void btnConfirmToken(ActionEvent event) {

		if (checkRequiredFields(List.of(fieldToken))) {
			alertFieldRequired(root2);
		} else {
			String tokenUser = fieldToken.getText();
			String tokenEmail = String.valueOf(token);

			if (tokenEmail.equals(tokenUser)) {
				validateFields++;
				loadView(VIEWS_NAMES.FORGOT_PASSWORD_RESET_TOKEN, root2);
			} else {
				Alert.show("Token inválido",
						"O token está inválido, por favor insira o token enviado para o seu e-mail.", root2);

			}
		}
	}

	@FXML
	public void btnBackLoginRoot1(MouseEvent event) {
		loadView(VIEWS_NAMES.LOGIN, root1);
	}

	@FXML
	public void btnBackLoginRoot2(MouseEvent event) {
		validateFields--;
		loadView(VIEWS_NAMES.FORGOT_PASSWORD, root2);
	}

	@FXML
	public void btnBackLoginRoot3(MouseEvent event) {
		validateFields--;
		loadView(VIEWS_NAMES.FORGOT_PASSWORD_TOKEN, root3);
	}

	public void loadView(VIEWS_NAMES view, StackPane pane) {
		new LoadViews().load(pane, view);
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		switch (validateFields) {
		case 1:
			fieldRequired(List.of(fieldEmail));
			break;

		case 2:
			fieldRequired(List.of(fieldToken));
			break;

		case 3:
			fieldRequired(List.of(fieldConfirmPassword, fieldPassword));
			break;
		}

	}

	private void fieldRequired(List<IFXValidatableControl> list) {
		validator = new RequiredFieldValidator();
		validator.setMessage("Campo obrigatório!");

		Validator.validate(validator, list);
	}

	private boolean checkRequiredFields(List<JFXTextField> list) {
		boolean validate;

		for (JFXTextField var : list) {

			validate = var.getText().trim().isEmpty();

			if (validate) {
				return true;
			}
		}
		return false;
	}
}

package br.com.senac.school.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import br.com.senac.school.dao.UsuarioDao;
import br.com.senac.school.dao.UsuarioDaoImpl;
import br.com.senac.school.email.EmailSender;
import br.com.senac.school.email.GmailService;
import br.com.senac.school.email.Token;
import br.com.senac.school.log.Logs;
import br.com.senac.school.model.Usuario;
import br.com.senac.school.service.MessageService;
import br.com.senac.school.session.Session;
import br.com.senac.school.util.Alert;
import br.com.senac.school.util.LoadViews;
import br.com.senac.school.util.VIEWS_NAMES;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class SendEmailController implements Initializable {

	@FXML
	private JFXTextField fieldToken;

	@FXML
	private StackPane root;

	@FXML
	private ImageView imageEmail;

	@FXML
	private ImageView spinner;

	@FXML
	private Label labelLoading;

	@FXML
	private JFXButton btnRegistration;

	@FXML
	private Label labelResendToken;

	@FXML
	private Label btnResendToken;

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
		service.send(MessageService.registration(usuario.getEmail(), token));
	}

	@FXML
	void btnRegistration(ActionEvent actionEvent) {
		imageEmail.setVisible(false);
		spinner.setVisible(true);
		labelLoading.setVisible(true);
		btnRegistration.setDisable(true);
		btnResendToken.setDisable(true);
		labelResendToken.setDisable(true);
		fieldToken.setDisable(true);

		String token = fieldToken.getText();

		if (token.equals(String.valueOf(this.token))) {
			Logs.info("Realizando cadastro");
			register.start();
			register.setOnSucceeded((event) -> {
				loadDashboard();
			});

		} else {
			imageEmail.setVisible(true);
			spinner.setVisible(false);
			labelLoading.setVisible(false);
			btnRegistration.setDisable(false);
			btnResendToken.setDisable(false);
			labelResendToken.setDisable(false);
			fieldToken.setDisable(false);
			Alert.show("Token inválido",
					"O token está inválido, por favor insira o token enviado para o seu e-mail para concluir o seu cadastro.",
					root);
		}
	}

	Service<Void> register = new Service<Void>() {
		@Override
		protected Task<Void> createTask() {
			return new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					int id = persistUsuario(usuario);
					usuario.setId(id);
					Session.setUsuario(usuario);
					return null;
				}
			};
		}

		protected void succeeded() {
			reset();
		};

	};

	@FXML
	void btnBackRegistration(MouseEvent event) {
		new LoadViews().load(root, VIEWS_NAMES.USER_REGISTER);
	}

	@FXML
	void btnResendToken(MouseEvent event) {
		Alert.show("Token enviado", "Enviamos novamente o token, por favor verifique o seu e-mail.", root);
		sendEmailResendToken();
	}

	private void loadDashboard() {
		new LoadViews().load(root, VIEWS_NAMES.DASHBOARD);
	}

	private int persistUsuario(Usuario usuario) {
		UsuarioDao dao = new UsuarioDaoImpl();
		return dao.save(usuario);

	}

}

package br.com.senac.school.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import br.com.senac.school.dao.ContactUsDaoImpl;
import br.com.senac.school.model.ContactUs;
import br.com.senac.school.model.Usuario;
import br.com.senac.school.session.Session;
import br.com.senac.school.util.Alert;
import br.com.senac.school.util.LoadViews;
import br.com.senac.school.util.MaskFX;
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

public class ContactUsController implements Initializable {

	@FXML
	private StackPane root;

	@FXML
	private JFXTextField fieldName;

	@FXML
	private JFXTextField fieldCpf;

	@FXML
	private JFXTextField fieldEmail;

	@FXML
	private JFXComboBox<String> fieldType;

	@FXML
	private JFXTextArea fieldMessage;

	@FXML
	private ImageView spinner;

	@FXML
	private Label labelSendMessage;

	@FXML
	private JFXButton btnSend;

	private RequiredFieldValidator validator;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		Usuario usuario = Session.getUsuario();

		if (usuario != null) {
			fieldEmail.setText(usuario.getEmail());
			fieldCpf.setText(usuario.getCpf());
			fieldName.setText(usuario.getNome());
		}
		fieldType.getItems().addAll(Arrays.asList("Crítica", "Dúvida", "Elogio", "Sugestão"));
		fieldRequired();
		MaskFX.cpfField(fieldCpf);
	}

	@FXML
	void btnSend(ActionEvent actionEvent) {

		if (checkRequiredFields()) {
			Alert.show("Campos obrigatórios", "Ops! Você precisa preencher os campos obrigatórios.", root);
		} else {
			setSpinnerOn();
			task.start();

			task.setOnSucceeded((event) -> {
				setSpinnerOff();

				Alert.show("Recebemos a sua mensagem!",
						"Obrigado por seu contato! Sua mensagem foi enviada para nossa equipe de atendimento.", root);

			});
		}
	}

	private void setSpinnerOn() {
		spinner.setVisible(true);
		labelSendMessage.setVisible(true);
		fieldCpf.setDisable(true);
		fieldEmail.setDisable(true);
		fieldMessage.setDisable(true);
		fieldName.setDisable(true);
		fieldType.setDisable(true);
		btnSend.setDisable(true);
	}

	private void setSpinnerOff() {
		spinner.setVisible(false);
		labelSendMessage.setVisible(false);
		fieldCpf.setDisable(false);
		fieldEmail.setDisable(false);
		fieldMessage.setDisable(false);
		fieldName.setDisable(false);
		fieldType.setDisable(false);
		btnSend.setDisable(false);
	}

	Service<Void> task = new Service<Void>() {

		@Override
		protected Task<Void> createTask() {

			return new Task<Void>() {
				protected Void call() throws Exception {
					new ContactUsDaoImpl().save(generate());
					return null;
				}
			};
		}

		protected void succeeded() {
			reset();
		};
	};

	private ContactUs generate() {
		String nome = fieldName.getText();
		String email = fieldEmail.getText();
		String cpf = fieldCpf.getText();
		String type = fieldType.getSelectionModel().getSelectedItem();
		String message = fieldMessage.getText();
		String data = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss"));
		return new ContactUs(nome, email, cpf, type, message, data);
	}

	private void fieldRequired() {
		validator = new RequiredFieldValidator();
		validator.setMessage("Campo obrigatório!");

		Validator.validate(validator, fieldCpf, fieldEmail, fieldMessage, fieldType, fieldName);
	}

	private boolean checkRequiredFields() {

		boolean cpf = fieldCpf.getText().trim().isEmpty();
		boolean email = fieldEmail.getText().trim().isEmpty();
		boolean message = fieldMessage.getText().trim().isEmpty();
		boolean name = fieldName.getText().trim().isEmpty();
		boolean type = fieldType.getSelectionModel().getSelectedItem() == null;

		List<Boolean> list = Arrays.asList(cpf, email, message, name, type);

		for (Boolean fieldRequired : list) {
			if (fieldRequired == true) {
				return true;
			}
		}
		return false;
	}

	@FXML
	void btnBackHome(MouseEvent event) {
		new LoadViews().load(root, VIEWS_NAMES.HOME);
	}
}

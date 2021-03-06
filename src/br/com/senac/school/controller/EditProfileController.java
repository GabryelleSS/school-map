package br.com.senac.school.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import br.com.senac.school.dao.UsuarioDaoImpl;
import br.com.senac.school.log.Logs;
import br.com.senac.school.model.Genero;
import br.com.senac.school.model.Usuario;
import br.com.senac.school.session.Session;
import br.com.senac.school.util.Alert;
import br.com.senac.school.util.DashboardPaneContent;
import br.com.senac.school.util.Encrypt;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class EditProfileController implements Initializable {

	@FXML
	private HBox formFieldsRegisterUser;

	@FXML
	private JFXTextField fieldFirstName;

	@FXML
	private JFXTextField fieldLastName;

	@FXML
	private JFXTextField fieldEmail;

	@FXML
	private JFXPasswordField fieldPassword;

	@FXML
	private JFXPasswordField fieldConfirmPassword;

	@FXML
	private JFXTextField fieldCPF;

	@FXML
	private JFXDatePicker fieldDateBirth;

	@FXML
	private JFXCheckBox fieldWoman;

	@FXML
	private JFXCheckBox fieldMan;

	@FXML
	private JFXCheckBox fieldNoGender;

	@FXML
	private JFXComboBox<String> fieldMaritalStatus;

	@FXML
	private JFXTextField fieldCell;

	@FXML
	private JFXTextField fieldTephone;

	@FXML
	private StackPane root;

	@FXML
	private AnchorPane rootEndereco;

	@FXML
	private ImageView spinner;

	@FXML
	private Label labelLoading;

	@FXML
	private Label labelGender;

	@FXML
	private JFXButton btnSaveProfile;

	@FXML
	private JFXButton btnNextEditProfile;

	public static Usuario usuario;

	private RequiredFieldValidator validator;

	@FXML
	void nextEditProfile(ActionEvent event) {
		generateUsuario();
		EditProfileEnderecoController.usuario = usuario;
		new LoadViews().load(root, VIEWS_NAMES.EDIT_PROFILE_ENDERECO);
	}

	@FXML
	void btnSaveProfile(ActionEvent actionEvent) {

		if (checkRequiredFields()) {
			Alert.show("Campos obrigat�rios", "Ops! Voc� precisa preencher os campos obrigat�rios.",
					DashboardPaneContent.root);

		} else {
			setSpinnerOn();
			task.start();
			task.setOnSucceeded((event) -> {
				setSpinnerOff();
				Alert.show("Altera��es efetuadas!", "Suas altera��es foram efetuadas com sucesso!",
						DashboardPaneContent.root);
			});
		}

	}

	private void setSpinnerOn() {
		labelLoading.setVisible(true);
		spinner.setVisible(true);
		fieldCell.setDisable(true);
		fieldConfirmPassword.setDisable(true);
		fieldCPF.setDisable(true);
		fieldDateBirth.setDisable(true);
		fieldEmail.setDisable(true);
		fieldFirstName.setDisable(true);
		fieldLastName.setDisable(true);
		fieldMan.setDisable(true);
		fieldMaritalStatus.setDisable(true);
		fieldNoGender.setDisable(true);
		fieldPassword.setDisable(true);
		fieldTephone.setDisable(true);
		fieldWoman.setDisable(true);
		labelGender.setDisable(true);
		btnNextEditProfile.setDisable(true);
		btnSaveProfile.setDisable(true);
	}
	private void setSpinnerOff() {
		labelLoading.setVisible(false);
		spinner.setVisible(false);
		fieldCell.setDisable(false);
		fieldConfirmPassword.setDisable(false);
		fieldCPF.setDisable(false);
		fieldDateBirth.setDisable(false);
		fieldEmail.setDisable(false);
		fieldFirstName.setDisable(false);
		fieldLastName.setDisable(false);
		fieldMan.setDisable(false);
		fieldMaritalStatus.setDisable(false);
		fieldNoGender.setDisable(false);
		fieldPassword.setDisable(false);
		fieldTephone.setDisable(false);
		fieldWoman.setDisable(false);
		labelGender.setDisable(false);
		btnNextEditProfile.setDisable(false);
		btnSaveProfile.setDisable(false);
	}

	Service<Void> task = new Service<Void>() {

		@Override
		protected Task<Void> createTask() {
			return new Task<Void>() {
				protected Void call() throws Exception {
					Logs.info("Realizando altera��es no perfil");
					generateUsuario();
					new UsuarioDaoImpl().update(usuario);
					return null;
				}
			};
		}

		protected void succeeded() {
			reset();
		};
	};

	private boolean checkRequiredFields() {
		boolean firstNameEmpty = fieldFirstName.getText().trim().isEmpty();
		boolean lastNameEmpty = fieldLastName.getText().trim().isEmpty();
		boolean email = fieldEmail.getText().trim().isEmpty();
		boolean cell = fieldCell.getText().trim().isEmpty();
		boolean telephone = fieldTephone.getText().trim().isEmpty();
		boolean cpf = fieldCPF.getText().trim().isEmpty();
		boolean birthDate = fieldDateBirth.getValue() == null;
		boolean maritalStatus = fieldMaritalStatus.getSelectionModel().getSelectedItem() == null;

		List<Boolean> fieldsRequireds = new ArrayList<>();

		fieldsRequireds.addAll(
				Arrays.asList(firstNameEmpty, lastNameEmpty, email, cell, cpf, birthDate, maritalStatus, telephone));

		if (confirmationSamePasswords()) {
			Alert.show("Senha incorreta", "Ops! As senhas est�o diferentes!", DashboardPaneContent.root);

			return true;
		} else {
			for (Boolean fieldRequired : fieldsRequireds) {
				if (fieldRequired == true) {
					return true;
				}
			}
			return false;
		}
	}

	private boolean confirmationSamePasswords() {
		String password = fieldPassword.getText();
		String confirmPassaword = fieldConfirmPassword.getText();

		if (password.equals(confirmPassaword)) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		usuario = Session.getUsuario();
		EditProfileEnderecoController.usuario = usuario;
		fields();
		maskFields();
		validator = new RequiredFieldValidator();
		validator.setMessage("Campo obrigat�rio!");
		fieldMaritalStatus.getItems().addAll("Solteiro", "Casado", "Separado", "Divorciado", "Viuvo");
		Validator.validate(validator, fieldCell, fieldCPF, fieldDateBirth, fieldEmail, fieldFirstName, fieldLastName,
				fieldMaritalStatus, fieldTephone);
	}

	private void maskFields() {
		MaskFX.cpfField(fieldCPF);
		MaskFX.dateField(fieldDateBirth);
		MaskFX.foneField(fieldCell);
		MaskFX.foneField(fieldTephone);
	}

	private void fields() {
		fieldCPF.setText(usuario.getCpf());
		fieldEmail.setText(usuario.getEmail());
		fieldCell.setText(usuario.getCelular());
		fieldDateBirth.setValue(usuario.getDataNascimento());

		String[] nome = usuario.getNome().split(" ");

		fieldFirstName.setText(nome[0]);
		if (nome.length > 1) {
			fieldLastName.setText(nome[1]);
		}
		fieldTephone.setText(usuario.getTelefone());

		String status = usuario.getEstadoCivil().charAt(0) + usuario.getEstadoCivil().substring(1).toLowerCase();

		fieldMaritalStatus.getSelectionModel().select(status);

		String sexo = usuario.getSexo();

		switch (sexo) {
		case "MASCULINO":
			fieldMan.setSelected(true);
			break;
		case "FEMININO":
			fieldWoman.setSelected(true);
			break;
		case "SEM_GENERO":
			fieldNoGender.setSelected(true);
			break;

		}
	}

	public void generateUsuario() {
		String nome = fieldFirstName.getText();
		String sobrenome = fieldLastName.getText();
		String cpf = fieldCPF.getText();
		LocalDate dataNascimento = fieldDateBirth.getValue();
		String telefone = fieldTephone.getText();
		String email = fieldEmail.getText();
		String senha = fieldPassword.getText();
		String celular = fieldCell.getText();
		String estadoCivil = fieldMaritalStatus.getSelectionModel().getSelectedItem();

		String genero;

		if (fieldMan.isSelected()) {
			genero = Genero.MASCULINO.toString();
		} else if (fieldWoman.isSelected()) {
			genero = Genero.FEMININO.toString();
		} else {
			genero = Genero.SEM_GENERO.toString();
		}

		usuario.setNome(nome.concat(" ").concat(sobrenome));
		usuario.setCpf(cpf);
		usuario.setDataNascimento(dataNascimento);
		usuario.setTelefone(telefone);
		usuario.setEmail(email);
		if (!senha.trim().isEmpty()) {
			usuario.setSenha(Encrypt.encode(senha));
		}
		usuario.setCelular(celular);
		usuario.setEstadoCivil(estadoCivil);
		usuario.setSexo(genero);

	}

}

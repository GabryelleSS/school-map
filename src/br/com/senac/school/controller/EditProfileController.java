package br.com.senac.school.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import br.com.senac.school.dao.UsuarioDaoImpl;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class EditProfileController implements Initializable {
	
	private static Logger logger = LogManager.getLogger(EditProfileController.class);

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
	private JFXButton btnBack;

	@FXML
	private JFXButton btnNextField;

	@FXML
	private StackPane root;

	@FXML
	private AnchorPane rootEndereco;

	public static Usuario usuario;

	private RequiredFieldValidator validator;

	@FXML
	void nextEditProfile(ActionEvent event) {
		generateUsuario();
		EditProfileEnderecoController.usuario = usuario;
		new LoadViews().load(root, VIEWS_NAMES.EDIT_PROFILE_ENDERECO);
	}

	@FXML
	void btnSaveProfile(ActionEvent event) {

		if (checkRequiredFields()) {
			Alert.show("Campos obrigatórios", "Ops! Você precisa preencher os campos obrigatórios.",
					DashboardPaneContent.root);

		} else {
			logger.info("Realizando alterações no perfil");
			generateUsuario();
			new UsuarioDaoImpl().update(usuario);
			Alert.show("Alterações efetuadas!", "Suas alterações foram efetuadas com sucesso!",
					DashboardPaneContent.root);
		}
	}

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
			Alert.show("Senha incorreta", "Ops! As senhas estão diferentes!", DashboardPaneContent.root);

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
		usuario = new UsuarioDaoImpl().findByEmail(Session.getUsuario().getEmail()).get(0);
		EditProfileEnderecoController.usuario = usuario;
		fields();
		maskFields();
		validator = new RequiredFieldValidator();
		validator.setMessage("Campo obrigatório!");
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
		if(nome.length>1) {
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

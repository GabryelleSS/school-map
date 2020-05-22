package br.com.senac.school.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.github.gilbertotorrezan.viacep.shared.ViaCEPEndereco;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;

import br.com.senac.school.factory.UsuarioFactory;
import br.com.senac.school.model.Genero;
import br.com.senac.school.model.Usuario;
import br.com.senac.school.service.ViaCEPService;
import br.com.senac.school.util.Alert;
import br.com.senac.school.util.LoadViews;
import br.com.senac.school.util.MaskFX;
import br.com.senac.school.util.VIEWS_NAMES;
import br.com.senac.school.util.Validator;
import consultaCep.Api;
import consultaCep.Endereco;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class UserRegisterController implements Initializable {
	@FXML
	private StackPane root;

	@FXML
	private JFXButton btnBack;

	@FXML
	private JFXButton btnNextField;

	@FXML
	private VBox formFieldsRegisterUserAddress;

	@FXML
	private JFXTextField fieldStreet;

	@FXML
	private JFXTextField fieldCep;

	@FXML
	private JFXTextField fieldNumber;

	@FXML
	private JFXTextField filedDistrict;

	@FXML
	private JFXTextField fieldComplement;

	@FXML
	private JFXTextField fieldCity;

	@FXML
	private JFXTextField fieldState;

	@FXML
	private JFXTextField fieldUF;

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
	private JFXCheckBox fieldContactEmail;

	@FXML
	private JFXCheckBox fieldContactTelefone;

	private double latitude;
	private double longitude;

	@FXML
	private JFXButton btnBackLogin;

	private ValidatorBase validator;

	private boolean nextFields;

	private boolean backHome;

	String warningModal = "Ops! Você precisa preencher os campos obrigatórios.";
	String warningModalPassawordIncorrect = "Ops! As senhas estão diferentes!";

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fieldRequired();
		maskFields();
		formFieldsRegisterUserAddress.setVisible(false);
		consultCep();
		backHome = true;
		fieldMaritalStatus.getItems().addAll("Solteiro", "Casado", "Separado", "Divorciado", "Viuvo");

		verifyCheckBoxPreferences();
	}

	private void verifyCheckBoxPreferences() {
		fieldContactEmail.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				fieldContactEmail.setSelected(true);
				fieldContactTelefone.setSelected(false);
			}
		});

		fieldContactTelefone.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				fieldContactEmail.setSelected(false);
				fieldContactTelefone.setSelected(true);
			}
		});
	}

	void fieldRequired() {
		validator = new RequiredFieldValidator();
		validator.setMessage("Campo obrigatório!");

		Validator.validate(validator, fieldCell, fieldCep, fieldCity, fieldConfirmPassword, fieldCPF, fieldDateBirth,
				fieldEmail, fieldFirstName, fieldLastName, fieldMaritalStatus, fieldNumber, fieldPassword, fieldState,
				fieldStreet, fieldTephone, fieldUF, filedDistrict);
	}

	private boolean checkRequiredFields() {
		boolean firstNameEmpty = fieldFirstName.getText().trim().isEmpty();
		boolean lastNameEmpty = fieldLastName.getText().trim().isEmpty();
		boolean email = fieldEmail.getText().trim().isEmpty();
		boolean password = fieldPassword.getText().trim().isEmpty();
		boolean passwordConfirm = fieldConfirmPassword.getText().trim().isEmpty();
		boolean cell = fieldCell.getText().trim().isEmpty();
		boolean telephone = fieldTephone.getText().trim().isEmpty();
		boolean cpf = fieldCPF.getText().trim().isEmpty();
		boolean birthDate = fieldDateBirth.getValue() == null;
		boolean maritalStatus = fieldMaritalStatus.getSelectionModel().getSelectedItem() == null;

		boolean cep = fieldCep.getText().trim().isEmpty();
		boolean city = fieldCity.getText().trim().isEmpty();
		boolean number = fieldNumber.getText().trim().isEmpty();
		boolean district = filedDistrict.getText().trim().isEmpty();
		boolean state = fieldState.getText().trim().isEmpty();
		boolean street = fieldStreet.getText().trim().isEmpty();
		boolean contactEmail = fieldContactEmail.isSelected();
		boolean contactTelephone = fieldContactTelefone.isSelected();
		boolean preferences = false;

		if (!contactEmail && !contactTelephone) {
			preferences = true;
		}

		List<Boolean> fieldsRequireds = new ArrayList<>();

		fieldsRequireds.addAll(Arrays.asList(firstNameEmpty, lastNameEmpty, email, password, passwordConfirm, cell, cpf,
				birthDate, maritalStatus, telephone));

		if (nextFields)
			fieldsRequireds.addAll(Arrays.asList(cep, city, number, district, state, street,preferences));

		if (confirmationSamePasswords()) {
			Alert.show("Senha incorreta", warningModalPassawordIncorrect, root);

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

	@FXML
	private void handleNextFields(ActionEvent event) throws InterruptedException {
		if (btnNextField.getText().equals("Cadastrar")) {

			if (checkRequiredFields()) {
				Alert.show("Campos obrigatórios", warningModal, root);

			} else {
				loadViewEmail();
			}
		} else {

			if (checkRequiredFields()) {
				Alert.show("Campos obrigatórios", warningModal, root);

			} else {
				formFieldsRegisterUser.setVisible(false);
				formFieldsRegisterUserAddress.setVisible(true);
				btnBack.setVisible(true);

				btnNextField.setText("Cadastrar");
				nextFields = true;
				backHome = false;
			}
		}
	}

	@FXML
	private void handleBackRegisterUser(ActionEvent event) {
		formFieldsRegisterUser.setVisible(true);
		formFieldsRegisterUserAddress.setVisible(false);
		String btnText = btnNextField.getText();

		if (backHome) {
			new LoadViews().load(root, VIEWS_NAMES.HOME);

		} else if (btnText == "Cadastrar") {
			btnNextField.setText("Próximo");
			nextFields = false;
			backHome = true;
		}
	}

	private void loadViewEmail() {
		SendEmailController.usuario = generateUsuario();
		new LoadViews().load(root, VIEWS_NAMES.SEND_EMAIL);

	}

	public void consultCep() {
		fieldCep.textProperty().addListener((observable, oldValue, newValue) -> {

			if (newValue.length() == 9) {

				Optional<ViaCEPEndereco> consulta = ViaCEPService.consulta(newValue);

				String[] cep = newValue.split("-");

				Endereco latELong = Api.buscaPorCep(cep[0].concat(cep[1]));

				latitude = latELong.getLatitude();
				longitude = latELong.getLongitude();

				if (consulta.isPresent()) {
					ViaCEPEndereco endereco = consulta.get();
					this.fieldStreet.setText(endereco.getLogradouro());
					this.filedDistrict.setText(endereco.getBairro());
					this.fieldUF.setText(endereco.getUf());
					this.fieldState.setText(endereco.getLocalidade());
				} else {
					Alert.show("CEP inválido", "Por favor insira um cep válido", root);
				}
			}

		});
	}

	public Usuario generateUsuario() {
		String nome = fieldFirstName.getText();
		String sobrenome = fieldLastName.getText();
		String cpf = fieldCPF.getText();
		LocalDate dataNascimento = fieldDateBirth.getValue();
		String telefone = fieldTephone.getText();
		String email = fieldEmail.getText();
		String senha = fieldPassword.getText();
		String celular = fieldCell.getText();
		String estadoCivil = fieldMaritalStatus.getSelectionModel().getSelectedItem();

		String preferenciaContato;

		if (fieldContactEmail.isSelected()) {
			preferenciaContato = "EMAIL";
		} else {
			preferenciaContato = "TELEFONE";
		}

		String genero;

		if (fieldMan.isSelected()) {
			genero = Genero.MASCULINO.toString();
		} else if (fieldWoman.isSelected()) {
			genero = Genero.FEMININO.toString();
		} else {
			genero = Genero.SEM_GENERO.toString();

		}

		String complement = "";
		if (!fieldComplement.getText().trim().isEmpty()) {
			complement = fieldComplement.getText();
		}

		String rua = fieldStreet.getText();
		String cep = fieldCep.getText();
		String numero = fieldNumber.getText();
		String bairro = filedDistrict.getText();
		String complemento = complement;
		String cidade = fieldCity.getText();
		String estado = fieldState.getText();
		String uf = fieldUF.getText();

		return UsuarioFactory.generate(nome.concat(" ").concat(sobrenome), cpf, dataNascimento, telefone, email, senha,
				celular, estadoCivil, genero, rua, cep, numero, bairro, complemento, cidade, estado, uf, latitude,
				longitude, preferenciaContato);
	}

	private void maskFields() {
		MaskFX.cepField(fieldCep);
		MaskFX.cpfField(fieldCPF);
		MaskFX.dateField(fieldDateBirth);
		MaskFX.foneField(fieldCell);
		MaskFX.foneField(fieldTephone);
		MaskFX.numericField(fieldNumber);
		MaskFX.maxField(fieldUF, 2);

	}
}

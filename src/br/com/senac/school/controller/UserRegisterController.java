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
import br.com.senac.school.util.VIEWS_NAMES;
import br.com.senac.school.util.Validator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
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
	private JFXButton btnBackLogin;

	private ValidatorBase validator;

	private boolean nextFields;

	String warningModal = "Ops! Você precisa preencher os campos obrigatórios.";
	String warningModalPassawordIncorrect = "Ops! As senhas estão diferentes!";

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fieldRequired();
		btnBack.setVisible(false);
		formFieldsRegisterUserAddress.setVisible(false);

		fieldMaritalStatus.getItems().addAll("Solteiro", "Casado", "Separado", "Divorciado", "Viuvo");

	}

	void fieldRequired() {
		validator = new RequiredFieldValidator();
		validator.setMessage("Campo obrigatório!");

		Validator.validate(validator, fieldCell, fieldCep, fieldCity, fieldComplement, fieldConfirmPassword, fieldCPF,
				fieldDateBirth, fieldEmail, fieldFirstName, fieldLastName, fieldMaritalStatus, fieldNumber,
				fieldPassword, fieldState, fieldStreet, fieldTephone, fieldUF, filedDistrict);
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
		boolean complement = fieldComplement.getText().trim().isEmpty();
		boolean number = fieldNumber.getText().trim().isEmpty();
		boolean district = filedDistrict.getText().trim().isEmpty();
		boolean state = fieldState.getText().trim().isEmpty();
		boolean street = fieldStreet.getText().trim().isEmpty();

		List<Boolean> fieldsRequireds = new ArrayList<>();

		fieldsRequireds.addAll(Arrays.asList(firstNameEmpty, lastNameEmpty, email, password, passwordConfirm, cell, cpf,
				birthDate, maritalStatus, telephone));

		if (nextFields)
			fieldsRequireds.addAll(Arrays.asList(cep, city, complement, number, district, state, street));

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
			}
		}
	}

	@FXML
	private void handleBackRegisterUser(ActionEvent event) {
		formFieldsRegisterUser.setVisible(true);
		btnBack.setVisible(false);
		formFieldsRegisterUserAddress.setVisible(false);

		String btnText = btnNextField.getText();
		if (btnText == "Cadastrar") {
			btnNextField.setText("Próximo");
			nextFields = false;
		}
	}

	@FXML
	private void backLogin() throws Exception {
		new LoadViews().load(root, VIEWS_NAMES.LOGIN);
	}

	private void loadViewEmail() {
		SendEmailController.usuario = generateUsuario();
		new LoadViews().load(root, VIEWS_NAMES.LOGIN);

	}

	@FXML
	void btnConsultarCep(KeyEvent event) {
		String cep = fieldCep.getText();

		if (cep.length() == 8) {
			Optional<ViaCEPEndereco> consulta = ViaCEPService.consulta(fieldCep.getText());

			if (consulta.isPresent()) {
				ViaCEPEndereco endereco = consulta.get();
				this.fieldStreet.setText(endereco.getLogradouro());
				this.fieldComplement.setText(endereco.getComplemento());
				this.filedDistrict.setText(endereco.getBairro());
				this.fieldUF.setText(endereco.getUf());
				this.fieldState.setText(endereco.getLocalidade());
			} else {
				Alert.show("CEP inválido", "Por favor insira um cep válido", root);
			}
		}

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

		String genero;

		if (fieldMan.isSelected()) {
			genero = Genero.MASCULINO.toString();
		} else if (fieldWoman.isSelected()) {
			genero = Genero.FEMININO.toString();
		} else {
			genero = Genero.SEM_GENERO.toString();

		}

		String rua = fieldStreet.getText();
		String cep = fieldCep.getText();
		String numero = fieldNumber.getText();
		String bairro = filedDistrict.getText();
		String complemento = fieldComplement.getText();
		String cidade = fieldCity.getText();
		String estado = fieldState.getText();
		String uf = fieldUF.getText();

		return UsuarioFactory.generate(nome.concat(" ").concat(sobrenome), cpf, dataNascimento, telefone, email, senha,
				celular, estadoCivil, genero, rua, cep, numero, bairro, complemento, cidade, estado, uf);
	}
}

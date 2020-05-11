package br.com.senac.school.controller;

import java.io.IOException;
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

import br.com.senac.school.builder.EnderecoBuilder;
import br.com.senac.school.builder.UsuarioBuilder;
import br.com.senac.school.factory.UsuarioFactory;
import br.com.senac.school.model.EnderecoUsuario;
import br.com.senac.school.model.Genero;
import br.com.senac.school.model.Usuario;
import br.com.senac.school.service.ViaCEPService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserRegisterController implements Initializable {
	@FXML
	private JFXTextField fieldFirstName;
	@FXML
	private JFXTextField fieldLastName;
	@FXML
	private JFXTextField fieldEmail;
	@FXML
	private JFXPasswordField txt;
	@FXML
	private JFXPasswordField fieldPassword;
	@FXML
	private JFXPasswordField fieldConfirmPassword;
	@FXML
	private JFXTextField fieldCPF;
	@FXML
	private JFXDatePicker fieldDateBirth;
	@FXML
	private JFXComboBox<String> fieldMaritalStatus;
	@FXML
	private JFXTextField fieldTephone;
	@FXML
	private JFXTextField fieldCell;
	@FXML
	private HBox formFieldsRegisterUser;
	@FXML
	private VBox formFieldsRegisterUserAddress;
	@FXML
	private Button btnNextField;
	@FXML
	private Button btnBack;
	@FXML
	private Pane modalWarning;
	@FXML
	private Text labelWarning;
	@FXML
	private JFXButton btnBackLogin;
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
	private JFXCheckBox fieldWoman;

	@FXML
	private JFXCheckBox fieldMan;

	@FXML
	private JFXCheckBox fieldNoGender;

	@FXML
	private AnchorPane userRegister;

	String warningModal = "Ops! Você precisa preencher os campos obrigatórios.";
	String warningModalPassawordIncorrect = "Ops! As senhas estão diferentes!";

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fieldRequired();

		btnBack.setVisible(false);
		modalWarning.setVisible(false);
		formFieldsRegisterUserAddress.setVisible(false);

		fieldMaritalStatus.getItems().addAll("Solteiro", "Casado", "Separado", "Divorciado", "Viuvo");
	}

	void fieldRequired() {
		RequiredFieldValidator validator = new RequiredFieldValidator();
		validator.setMessage("Campo obrigatório!");

		fieldFirstName.getValidators().add(validator);
		fieldFirstName.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				fieldFirstName.validate();
			}
		});

		fieldLastName.getValidators().add(validator);
		fieldLastName.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				fieldLastName.validate();
			}
		});

		fieldEmail.getValidators().add(validator);
		fieldEmail.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				fieldEmail.validate();
			}
		});

		fieldPassword.getValidators().add(validator);
		fieldPassword.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				fieldPassword.validate();
			}
		});

		fieldConfirmPassword.getValidators().add(validator);
		fieldConfirmPassword.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				fieldConfirmPassword.validate();
			}
		});

		fieldCell.getValidators().add(validator);
		fieldCell.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				fieldCell.validate();
			}
		});
	}

	private boolean checkRequiredFields() {
		boolean firstNameEmpty = fieldFirstName.getText().trim().isEmpty();
		boolean lastNameEmpty = fieldLastName.getText().trim().isEmpty();
		boolean email = fieldEmail.getText().trim().isEmpty();
		boolean password = fieldPassword.getText().trim().isEmpty();
		boolean passwordConfirm = fieldConfirmPassword.getText().trim().isEmpty();
		boolean cell = fieldCell.getText().trim().isEmpty();

		ArrayList<Boolean> fieldsRequireds = new ArrayList();

		fieldsRequireds.add(firstNameEmpty);
		fieldsRequireds.add(lastNameEmpty);
		fieldsRequireds.add(email);
		fieldsRequireds.add(password);
		fieldsRequireds.add(passwordConfirm);
		fieldsRequireds.add(cell);

		if (confirmationSamePasswords()) {
			labelWarning.setText(warningModalPassawordIncorrect);
			modalWarning.setVisible(true);

			return true;
		} else {
			for (Boolean fieldRequired : fieldsRequireds) {
				if (fieldRequired == true) {
					labelWarning.setText(warningModal);
					modalWarning.setVisible(true);
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
			loadViewEmail();
		}

		if (checkRequiredFields()) {
			System.out.println("Senha incorreta");
		} else {
			formFieldsRegisterUser.setVisible(false);
			formFieldsRegisterUserAddress.setVisible(true);
			btnBack.setVisible(true);

			btnNextField.setText("Cadastrar");
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
		}
	}

	@FXML
	private void btnConfirm(ActionEvent event) {
		modalWarning.setVisible(false);
	}

	@FXML
	private void backLogin() throws Exception {
			Parent login = FXMLLoader.load(getClass().getResource("/br/com/senac/school/view/Login.fxml"));

			userRegister.getChildren().clear();
			userRegister.getChildren().add(login);
		}

	private void loadViewEmail() {
		try {
			SendEmailController.usuario = generateUsuario();
			Parent pane = FXMLLoader.load(getClass().getResource("/br/com/senac/school/view/SendEmail.fxml"));
			userRegister.getChildren().clear();
			userRegister.getChildren().add(pane);

		} catch (IOException e) {
		}
	}

	@FXML
	public void btnConsultCEP(ActionEvent event) {
		
		Optional<ViaCEPEndereco> consulta = ViaCEPService.consulta(fieldCep.getText());
		
		if(consulta.isPresent()) {
			ViaCEPEndereco endereco = consulta.get();
			 this.fieldStreet.setText(endereco.getLogradouro());
			 this.fieldComplement.setText(endereco.getComplemento());
			 this.filedDistrict.setText(endereco.getBairro());
			 this.fieldUF.setText(endereco.getUf());
			 this.fieldState.setText(endereco.getLocalidade());
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

		return UsuarioFactory
				.generate(nome.concat(" ").concat(sobrenome), cpf, dataNascimento, telefone, email, senha,
				celular, estadoCivil, genero, rua, cep, numero, bairro, complemento, cidade, estado, uf);
	}
}

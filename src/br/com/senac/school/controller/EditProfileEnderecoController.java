package br.com.senac.school.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;

import com.github.gilbertotorrezan.viacep.shared.ViaCEPEndereco;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import br.com.senac.school.dao.UsuarioDaoImpl;
import br.com.senac.school.log.Logs;
import br.com.senac.school.model.EnderecoUsuario;
import br.com.senac.school.model.Usuario;
import br.com.senac.school.service.ViaCEPService;
import br.com.senac.school.util.Alert;
import br.com.senac.school.util.DashboardPaneContent;
import br.com.senac.school.util.LoadViews;
import br.com.senac.school.util.MaskFX;
import br.com.senac.school.util.VIEWS_NAMES;
import br.com.senac.school.util.Validator;
import consultaCep.Api;
import consultaCep.Endereco;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class EditProfileEnderecoController implements Initializable {

	@FXML
	private JFXTextField fieldRua;

	@FXML
	private JFXTextField fieldNumero;

	@FXML
	private JFXTextField fieldComplemento;

	@FXML
	private JFXTextField fieldEstado;

	@FXML
	private JFXTextField fieldBairro;

	@FXML
	private JFXTextField fieldCidade;

	@FXML
	private JFXTextField fieldUf;

	@FXML
	private JFXTextField fieldCep;

	@FXML
	private JFXCheckBox fieldContactEmail;

	@FXML
	private JFXCheckBox fieldContactTelefone;

	@FXML
	private ImageView spinner;

	@FXML
	private Label labelLoading;

	@FXML
	private StackPane root;

	public static Usuario usuario;

	private double latitude;
	private double longitude;

	private RequiredFieldValidator validator;

	@FXML
	void backEditProfile(ActionEvent event) {
		if (checkRequiredFields()) {
			Alert.show("Campos obrigatórios", "Ops! Você precisa preencher os campos obrigatórios.",
					DashboardPaneContent.root);
		} else {
			generateUsuario();
			EditProfileController.usuario = usuario;
			new LoadViews().load(root, VIEWS_NAMES.EDIT_PROFILE);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		verifyCheckBoxPreferences();
		fields();
		maskFields();
		consultCep();
		validator = new RequiredFieldValidator();
		validator.setMessage("Campo obrigatório!");
		Validator.validate(validator, fieldCep, fieldBairro, fieldCidade, fieldEstado, fieldNumero, fieldRua, fieldUf);
	}

	private void maskFields() {
		MaskFX.cepField(fieldCep);
		MaskFX.numericField(fieldNumero);
		MaskFX.maxField(fieldUf, 2);
	}

	private boolean checkRequiredFields() {
		boolean cep = fieldCep.getText().trim().isEmpty();
		boolean bairro = fieldBairro.getText().trim().isEmpty();
		boolean cidade = fieldCidade.getText().trim().isEmpty();
		boolean estado = fieldEstado.getText().trim().isEmpty();
		boolean numero = fieldNumero.getText().trim().isEmpty();
		boolean rua = fieldRua.getText().trim().isEmpty();
		boolean uf = fieldUf.getText().trim().isEmpty();
		boolean contactEmail = fieldContactEmail.isSelected();
		boolean contactTelephone = fieldContactTelefone.isSelected();

		boolean preferences = false;

		if (!contactEmail && !contactTelephone) {
			preferences = true;
		}

		List<Boolean> fieldsRequireds = new ArrayList<>();

		fieldsRequireds.addAll(Arrays.asList(cep, bairro, cidade, estado, numero, rua, uf, preferences));

		for (Boolean fieldRequired : fieldsRequireds) {
			if (fieldRequired == true) {
				return true;
			}
		}
		return false;
	}

	private void fields() {
		EnderecoUsuario endereco = usuario.getEndereco();

		fieldBairro.setText(endereco.getBairro());
		fieldCep.setText(endereco.getCep());
		fieldCidade.setText(endereco.getCidade());
		fieldComplemento.setText(endereco.getComplemento());
		fieldEstado.setText(endereco.getEstado());
		fieldNumero.setText(String.valueOf(endereco.getNumero()));
		fieldRua.setText(endereco.getEndereco());
		fieldUf.setText(endereco.getUf());

		String preferenciaContato = usuario.getPreferenciaContato();
		if (preferenciaContato.equals("EMAIL")) {
			fieldContactEmail.setSelected(true);
		} else {
			fieldContactTelefone.setSelected(true);
		}
	}

	public void consultCep() {
		fieldCep.textProperty().addListener((observable, oldValue, newValue) -> {

			if (newValue.length() == 9) {
				spinner.setVisible(true);
				labelLoading.setVisible(true);
				labelLoading.setText("Consultando CEP..");
				labelLoading.setLayoutX(407);
				labelLoading.setLayoutY(63);

				Executors.newFixedThreadPool(10).submit(() -> {

					Optional<ViaCEPEndereco> consulta = ViaCEPService.consulta(newValue);

					String[] cep = newValue.split("-");

					Endereco latELong = Api.buscaPorCep(cep[0].concat(cep[1]));

					latitude = latELong.getLatitude();
					longitude = latELong.getLongitude();

					if (consulta.isPresent()) {
						ViaCEPEndereco endereco = consulta.get();
						this.fieldRua.setText(endereco.getLogradouro());
						this.fieldBairro.setText(endereco.getBairro());
						this.fieldUf.setText(endereco.getUf());
						this.fieldEstado.setText(endereco.getLocalidade());
						spinner.setVisible(false);
						labelLoading.setVisible(false);
					} else {
						spinner.setVisible(false);
						labelLoading.setVisible(false);
						Alert.show("CEP inválido", "Por favor insira um cep válido", DashboardPaneContent.root);
					}

				});
			}

		});
	}

	@FXML
	void btnSaveUpdate(ActionEvent actionEvent) {

		if (checkRequiredFields()) {
			Alert.show("Campos obrigatórios", "Ops! Você precisa preencher os campos obrigatórios.",
					DashboardPaneContent.root);

		} else {
			spinner.setVisible(true);
			labelLoading.setVisible(true);
			labelLoading.setText("Salvando..");
			labelLoading.setLayoutX(431);
			labelLoading.setLayoutY(63);
			task.start();
			task.setOnSucceeded((event) -> {
				spinner.setVisible(false);
				labelLoading.setVisible(false);
				Alert.show("Alterações efetuadas!", "Suas alterações foram efetuadas com sucesso!",
						DashboardPaneContent.root);
			});
		}
	}

	Service<Void> task = new Service<Void>() {

		@Override
		protected Task<Void> createTask() {
			return new Task<Void>() {
				protected Void call() throws Exception {
					Logs.info("Realizando alterações no perfil");
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

	public void generateUsuario() {
		String rua = fieldRua.getText();
		String bairro = fieldBairro.getText();
		String cep = fieldCep.getText();
		String cidade = fieldCidade.getText();
		String estado = fieldEstado.getText();
		String numero = fieldNumero.getText();
		String uf = fieldUf.getText();

		String complemento = fieldComplemento.getText();
		if (fieldComplemento.getText().trim().isEmpty()) {
			complemento = "";
		}

		String preferenciaContato;

		if (fieldContactEmail.isSelected()) {
			preferenciaContato = "EMAIL";
		} else {
			preferenciaContato = "TELEFONE";
		}

		EnderecoUsuario endereco = usuario.getEndereco();
		endereco.setEndereco(rua);
		endereco.setBairro(bairro);
		endereco.setCep(cep);
		endereco.setCidade(cidade);
		endereco.setEstado(estado);
		endereco.setNumero(Integer.valueOf(numero));
		endereco.setUf(uf);
		endereco.setComplemento(complemento);

		usuario.setPreferenciaContato(preferenciaContato);

		if (latitude != 0 && longitude != 0) {
			endereco.setLatitude(latitude);
			endereco.setLongitude(longitude);
		}

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

}

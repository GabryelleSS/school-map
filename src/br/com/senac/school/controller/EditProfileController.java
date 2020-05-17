package br.com.senac.school.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import br.com.senac.school.model.Usuario;
import br.com.senac.school.session.Session;
import br.com.senac.school.util.VIEWS_NAMES;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

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
	private JFXButton btnBack;

	@FXML
	private JFXButton btnNextField;

	@FXML
	private AnchorPane root;

	@FXML
	private AnchorPane rootEndereco;

	private Usuario usuario;

	@FXML
	void nextEditProfile(ActionEvent event) {

		loadView(VIEWS_NAMES.EDIT_PROFILE_ENDERECO, root);

	}

	@FXML
	void handleBackRegisterUser(ActionEvent event) {

	}

	@FXML
	void handleNextFields(ActionEvent event) {

	}

	public void loadView(VIEWS_NAMES view, AnchorPane root) {
		try {
			Parent pane = FXMLLoader.load(getClass().getResource(view.getName()));

			root.getChildren().clear();
			root.getChildren().add(pane);

		} catch (Exception e) {

		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (usuario == null) {
			usuario = Session.getUsuario();
		}
		fields();
	}

	private void fields() {
		fieldCPF.setText(usuario.getCpf());
		fieldEmail.setText(usuario.getEmail());
		fieldCell.setText(usuario.getCelular());
		fieldDateBirth.setValue(usuario.getDataNascimento());

		String[] nome = usuario.getNome().split(" ");

		fieldFirstName.setText(nome[0]);
		fieldLastName.setText(nome[1]);
		fieldTephone.setText(usuario.getTelefone());
	}

}

package br.com.senac.school.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import br.com.senac.school.model.EnderecoUsuario;
import br.com.senac.school.model.Usuario;
import br.com.senac.school.session.Session;
import br.com.senac.school.util.VIEWS_NAMES;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

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
	private AnchorPane rootEndereco;

	private Usuario usuario;

	@FXML
	void backEditProfile(ActionEvent event) {
		loadView(VIEWS_NAMES.EDIT_PROFILE, rootEndereco);
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
		EnderecoUsuario endereco = usuario.getEndereco();

		fieldBairro.setText(endereco.getBairro());
		fieldCep.setText(endereco.getCep());
		fieldCidade.setText(endereco.getCidade());
		fieldComplemento.setText(endereco.getComplemento());
		fieldEstado.setText(endereco.getEstado());
		fieldNumero.setText(String.valueOf(endereco.getNumero()));
		fieldRua.setText(endereco.getEndereco());
		fieldUf.setText(endereco.getUf());
	}

}

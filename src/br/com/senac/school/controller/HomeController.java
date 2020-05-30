package br.com.senac.school.controller;

import br.com.senac.school.util.LoadViews;
import br.com.senac.school.util.VIEWS_NAMES;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class HomeController {

	@FXML
	private StackPane root;

	@FXML
	void btnRegister(ActionEvent event) {
		new LoadViews().load(root, VIEWS_NAMES.USER_REGISTER);

	}

	@FXML
	void btnRegisterTop(ActionEvent event) {
		new LoadViews().load(root, VIEWS_NAMES.USER_REGISTER);

	}

	@FXML
	void btnLogin(MouseEvent event) {
		new LoadViews().load(root, VIEWS_NAMES.LOGIN);

	}

	@FXML
	void btnContact(MouseEvent event) {
		new LoadViews().load(root, VIEWS_NAMES.CONTACT_US_MENU);
	}

}

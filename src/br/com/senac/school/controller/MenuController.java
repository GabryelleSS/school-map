package br.com.senac.school.controller;

import br.com.senac.school.session.Session;
import br.com.senac.school.util.VIEWS_NAMES;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class MenuController {

	private MenuCallback callback;

	public void setCallback(MenuCallback callback) {
		this.callback = callback;
	}

	@FXML
	void configurations(MouseEvent event) {

	}

	@FXML
	void editProfile(MouseEvent event) {
		callback.updateViewContent(VIEWS_NAMES.EDIT_PROFILE);
	}

	@FXML
	void home(MouseEvent event) {
		callback.updateView(VIEWS_NAMES.DASHBOARD);

	}

	@FXML
	void logout(MouseEvent event) {
		Session.removeUsuario();
		callback.updateView(VIEWS_NAMES.LOGIN);
	}

}

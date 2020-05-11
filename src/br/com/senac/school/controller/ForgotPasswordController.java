package br.com.senac.school.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class ForgotPasswordController {

	@FXML
	private AnchorPane root;

	@FXML
	public void btnBackLogin(ActionEvent event) throws IOException {
		Parent pane = FXMLLoader.load(getClass().getResource("/br/com/senac/school/view/Login.fxml"));

		root.getChildren().clear();
		root.getChildren().add(pane);

	}

}

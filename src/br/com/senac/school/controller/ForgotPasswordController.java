package br.com.senac.school.controller;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class ForgotPasswordController {

    @FXML
    private AnchorPane root;
	
	@FXML
    private void handleSendForgotPassword(ActionEvent event) {
		loadForgotPassword();
    }
	
	private void loadForgotPassword() {
		try {
			AnchorPane parentContent = FXMLLoader.load(getClass().getResource(("/br/com/senac/school/view/SendEmail.fxml")));
			root.getChildren().setAll(parentContent);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	@FXML
    void handleBackLogin(ActionEvent event) {
		try {
			AnchorPane parentContent = FXMLLoader.load(getClass().getResource(("/br/com/senac/school/view/Login.fxml")));
			root.getChildren().setAll(parentContent);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
}

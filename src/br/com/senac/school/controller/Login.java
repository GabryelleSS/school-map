package br.com.senac.school.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Login implements Initializable {
	
	@FXML
    private AnchorPane root;
	@FXML
	private Stage Dashboard;

    @FXML
    private JFXButton login;

    @FXML
    private JFXButton btnUserRegister;

    @FXML
    void btnLogin(ActionEvent event) throws IOException {
    	System.out.println("eie");
    }
	
	@FXML
    private void btnUserRegister(ActionEvent event) {
		
		try {
			AnchorPane parentContent = FXMLLoader.load(getClass().getResource(("/br/com/senac/school/view/UserRegister.fxml")));
			root.getChildren().setAll(parentContent);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    }
	
	@FXML
    void handleForgotPassaword(ActionEvent event) {
		
		try {
			AnchorPane parentContent = FXMLLoader.load(getClass().getResource(("/br/com/senac/school/view/UserRegister.fxml")));
			root.getChildren().setAll(parentContent);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    }
	  
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
}

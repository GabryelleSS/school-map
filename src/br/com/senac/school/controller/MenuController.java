package br.com.senac.school.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuController implements Initializable {

	private DashboardController callback;

//	@Override
//	public void start(Stage stage) throws Exception {
//		Parent root = FXMLLoader.load(getClass().getResource("/br/com/senac/school/view/Dashboard.fxml"));
//		
//		Scene scene = new Scene(root);
//		
//		stage.setScene(scene);
//		stage.show();
//		
//	}

	public void setCallback(DashboardController callback) {
		 this.callback = callback;
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
}

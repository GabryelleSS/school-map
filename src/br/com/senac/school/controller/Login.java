package br.com.senac.school.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Login implements Initializable {
	
	@FXML
	private Stage UserRegister;
	
	@FXML
    private JFXButton btnUserRegister;
	
	@FXML
    private void btnUserRegister() throws Exception {
		if(UserRegister == null || !UserRegister.isShowing()) {
            Parent userRegister = FXMLLoader.load(
                getClass().getResource(
                    "/br/com/senac/school/view/UserRegister.fxml"
                )
            );
            
            UserRegister = new Stage();
            Scene scene = new Scene(userRegister);

            UserRegister.setScene(scene);
            UserRegister.setTitle("School Map");
            UserRegister.show();
        
            Stage stage = (Stage) btnUserRegister.getScene().getWindow();
            stage.close();
        }
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
}

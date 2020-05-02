package br.com.senac.school.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class UserRegisterController {
	
	@FXML
    private TextField fieldFirstName;
    @FXML
    private TextField fieldFirstLastName;
    @FXML
    private TextField fieldCPF;
    @FXML
    private DatePicker fieldDateBirth;
    @FXML
    private TextField fieldMaritalStatus;
    @FXML
    private TextField fieldCell;
    @FXML
    private TextField fieldTephone;
    @FXML
    private Pane modalNotificationWarning;
    @FXML
    private Label textNotification;
    @FXML
    private HBox formFieldsRegisterUser;
    @FXML
    private VBox formFieldsRegisterUserAddress;
    
    @FXML
    private void handleNextFields(ActionEvent event) {
    	formFieldsRegisterUser.setStyle("-fx-opacity: 0;");
    	formFieldsRegisterUserAddress.setStyle("-fx-opacity: 1;");
    }
    
    @FXML
    private void handleBackRegisterUser(ActionEvent event) {
    	formFieldsRegisterUser.setStyle("-fx-opacity: 1;");
    	formFieldsRegisterUserAddress.setStyle("-fx-opacity: 0;");
    }
	
}

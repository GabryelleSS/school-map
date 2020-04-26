package br.com.senac.school.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

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
	private void handleRegisterUser() throws Exception {
		
		boolean checkFieldFirstNameEmpty = fieldFirstLastName.getText().trim().isEmpty();
//		boolean checkFieldLastNameEmpty = fieldFirstLastName.getText().trim().isEmpty();
//		boolean checkFieldCPFEmpty = fieldCPF.getText().trim().isEmpty();
//		boolean checkFieldDateBirthEmpty = fieldDateBirth.getText().trim().isEmpty();
//		boolean checkFieldCellEmpty = fieldCell.getText().trim().isEmpty();
//		boolean checkFieldTephoneEmpty = fieldTephone.getText().trim().isEmpty();
		
		if (checkFieldFirstNameEmpty) {
			modalNotificationWarning();
    	}
	}
	
	@FXML
	private void handleBackInit() throws Exception {
		System.out.print("Tela inicial");
	}
	
	@FXML
	private void handleConfirmNotificationWarning() throws Exception {
		modalNotificationWarning.setStyle("-fx-opacity: 0;");
	}
	

	@FXML
	private void modalNotificationWarning() {
		modalNotificationWarning.setStyle("-fx-opacity: 1;");
		textNotification.setText("Você não preencheu o seu " + "!");
		
		fieldFirstName.getStyleClass().clear();
		fieldFirstName.getStyleClass().add("user-register-field--isRequired");
	}
	
}

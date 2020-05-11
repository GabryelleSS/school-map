package br.com.senac.school.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class UserRegisterController implements Initializable {
	@FXML
    private AnchorPane root;
	@FXML
    private JFXTextField fieldFirstName;
    @FXML
    private JFXTextField fieldLastName;
    @FXML
    private JFXTextField fieldEmail;
    @FXML
    private JFXPasswordField txt;
    @FXML
    private JFXPasswordField fieldPassword;
    @FXML
    private JFXPasswordField fieldConfirmPassword;
    @FXML
    private JFXTextField fieldCPF;
    @FXML
    private JFXDatePicker fieldDateBirth;
    @FXML
    private JFXComboBox<String> fieldMaritalStatus;
    @FXML
    private JFXTextField fieldTephone;
    @FXML
    private JFXTextField fieldCell;
    @FXML
    private HBox formFieldsRegisterUser;
    @FXML
    private VBox formFieldsRegisterUserAddress;
    @FXML
    private Button btnNextField;
    @FXML
    private Button btnBack;
    @FXML
    private Pane modalWarning;
    @FXML
    private Text labelWarning;
    @FXML
    private JFXButton btnBackLogin;
    
	String warningModal = "Ops! Você precisa preencher os campos obrigatórios.";
	String warningModalPassawordIncorrect = "Ops! As senhas estão diferentes!";
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	fieldRequired();
    	
    	btnBack.setVisible(false);
    	modalWarning.setVisible(false);
    	formFieldsRegisterUserAddress.setVisible(false);
    	
    	fieldMaritalStatus.getItems().addAll(
	        "Solteiro",
	        "Casado",
	        "Separado",
	        "Divorciado",
	        "Viúvo"
	    );
	}
    
    void fieldRequired() {
    	RequiredFieldValidator validator = new RequiredFieldValidator();
    	validator.setMessage("Campo obrigatório!");
    	
    	fieldFirstName.getValidators().add(validator);
    	fieldFirstName.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
            	fieldFirstName.validate();
            }
        });
    	
    	fieldLastName.getValidators().add(validator);
    	fieldLastName.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
            	fieldLastName.validate();
            }
        });
    	
    	fieldEmail.getValidators().add(validator);
    	fieldEmail.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
            	fieldEmail.validate();
            }
        });
    	
    	fieldPassword.getValidators().add(validator);
    	fieldPassword.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
            	fieldPassword.validate();
            }
        });
    	
    	fieldConfirmPassword.getValidators().add(validator);
    	fieldConfirmPassword.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
            	fieldConfirmPassword.validate();
            }
        });
    	
    	fieldCell.getValidators().add(validator);
    	fieldCell.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
            	fieldCell.validate();
            }
        });
    }
    
    private boolean checkRequiredFields() {    	
    	boolean firstNameEmpty = fieldFirstName.getText().trim().isEmpty();
    	boolean lastNameEmpty = fieldLastName.getText().trim().isEmpty();
    	boolean email = fieldEmail.getText().trim().isEmpty();
    	boolean password = fieldPassword.getText().trim().isEmpty();
		boolean passwordConfirm = fieldConfirmPassword.getText().trim().isEmpty();
		boolean cell = fieldCell.getText().trim().isEmpty();
		
		ArrayList<Boolean> fieldsRequireds = new ArrayList();
		
		fieldsRequireds.add(firstNameEmpty);
		fieldsRequireds.add(lastNameEmpty);
		fieldsRequireds.add(email);
		fieldsRequireds.add(password);
		fieldsRequireds.add(passwordConfirm);
		fieldsRequireds.add(cell);
		
		if (confirmationSamePasswords()) {
			labelWarning.setText(warningModalPassawordIncorrect);
    		modalWarning.setVisible(true);
    		
    		return true;
		}
		else {
			for (Boolean fieldRequired : fieldsRequireds) {
				if (fieldRequired == true) {
					labelWarning.setText(warningModal);
		    		modalWarning.setVisible(true);
		    		return true;
				}
			}
			return false;
		}		
    }
    
    private boolean confirmationSamePasswords() {
    	String password = fieldPassword.getText();
    	String confirmPassaword = fieldConfirmPassword.getText();
    	
    	if (password.equals(confirmPassaword)) {
    		return false;
    	} else {
    		return true;
    	}
    }
    
    @FXML
    private void handleNextFields(ActionEvent event) {
    	
    	if (checkRequiredFields()) {
    		System.out.println("Senha incorreta");
    	} else {
        	formFieldsRegisterUser.setVisible(false);
        	formFieldsRegisterUserAddress.setVisible(true);
        	btnBack.setVisible(true);
        	
        	btnNextField.setText("Cadastrar");
    	}
    }
    
    @FXML
    private void handleBackRegisterUser(ActionEvent event) {
    	formFieldsRegisterUser.setVisible(true);
    	btnBack.setVisible(false);
    	formFieldsRegisterUserAddress.setVisible(false);
    	
    	String btnText = btnNextField.getText();
    	if (btnText == "Cadastrar") {
        	btnNextField.setText("Próximo");
    	}
    }
    
    @FXML
    private void btnConfirm(ActionEvent event) { 
    	modalWarning.setVisible(false);
    }
    
    @FXML
    private void backLogin(ActionEvent event) {
    	
    	try {
			AnchorPane parentContent = FXMLLoader.load(getClass().getResource(("/br/com/senac/school/view/Login.fxml")));
			root.getChildren().setAll(parentContent);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    	
}

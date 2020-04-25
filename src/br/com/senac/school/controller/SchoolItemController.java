package br.com.senac.school.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.senac.school.model.School;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;

public class SchoolItemController implements Initializable {

    @FXML
    private Label endereco;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    }

    public void setTask(School model) {
        ContextMenu menu = new ContextMenu();
        endereco.setText(model.getName());
        endereco.setContextMenu(menu);
    }

}

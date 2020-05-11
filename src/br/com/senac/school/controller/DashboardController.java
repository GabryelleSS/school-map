package br.com.senac.school.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;

public class DashboardController implements Initializable {

	@FXML
	private AnchorPane archorPane;

	@FXML
	private JFXDrawer drawer;

	@FXML
	private JFXHamburger menuHamburguer;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		menuHamburger();
		paneMenu();
	}

	private void paneMenu() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/senac/school/view/Menu.fxml"));
			VBox box = loader.load();
			drawer.setSidePane(box);
		} catch (Exception e) {
		}
	}

	private void menuHamburger() {
		HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(menuHamburguer);

		transition.setRate(-1);

		menuHamburguer.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
			transition.setRate(transition.getRate() * -1);
			transition.play();

			if (drawer.isShown()) {
				drawer.close();
			} else {
				drawer.open();
			}
		});
	}

}

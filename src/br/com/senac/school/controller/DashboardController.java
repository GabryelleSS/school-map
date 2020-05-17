package br.com.senac.school.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import br.com.senac.school.util.VIEWS_NAMES;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class DashboardController implements Initializable, MenuCallback {

	@FXML
	private AnchorPane root;
	@FXML
	private VBox content;

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
			MenuController controller = loader.getController();
			controller.setCallback(this);
			drawer.setSidePane(box);
		} catch (Exception e) {
			e.printStackTrace();
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

	@Override
	public void updateViewContent(VIEWS_NAMES view) {
		try {
			Parent pane = FXMLLoader.load(getClass().getResource(view.getName()));

			content.getChildren().clear();
			content.getChildren().add(pane);

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void updateView(VIEWS_NAMES view) {
		try {
			Parent pane = FXMLLoader.load(getClass().getResource(view.getName()));

			root.getChildren().clear();
			root.getChildren().add(pane);

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@FXML
	void labelHome(MouseEvent event) {
		updateView(VIEWS_NAMES.DASHBOARD);
	}
}

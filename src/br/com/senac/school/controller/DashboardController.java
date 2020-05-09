package br.com.senac.school.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import sun.util.logging.PlatformLogger.Level;
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
//		try {
//			FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/senac/school/view/Menu.fxml"));
//			VBox box = loader.load();
//			
//			MenuController controller = loader.getController();
//            controller.setCallback(this);
//            drawer.setSidePane(box);
//		} catch(IOException ex) {
//			Logger.getLogger(DashboardController.class.getName()).log(null);
//			System.out.print("erro");
//		}
//		
//		
//		HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(menuHamburguer);
//		
//		transition.setRate(-1);
//		
//        menuHamburguer.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
//            transition.setRate(transition.getRate() * -1);
//            transition.play();
//
//            if (drawer.isShowing()) {
//                drawer.close();
//            } else {
//                drawer.open();
//            }
//        });
		
	}
}

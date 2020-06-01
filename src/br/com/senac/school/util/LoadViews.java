package br.com.senac.school.util;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class LoadViews {

	public void load(StackPane pane, VIEWS_NAMES name) {

		new Thread(() -> {

			try {
				URL url = getClass().getResource(name.getName());
				Parent view = FXMLLoader.load(url);
				pane.getChildren().clear();
				pane.getChildren().add(view);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).run();
	}

}

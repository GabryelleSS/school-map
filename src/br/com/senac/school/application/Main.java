package br.com.senac.school.application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/senac/school/view/Splah.fxml"));
			AnchorPane pane = loader.load();
			Scene mainScene = new Scene(pane);
			primaryStage.setScene(mainScene);
			primaryStage.setTitle("School Map");
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}

package br.com.senac.school.application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/senac/school/view/Splash.fxml"));
			Parent pane = loader.load();
			Scene mainScene = new Scene(pane);
			primaryStage.setScene(mainScene);
			primaryStage.setTitle("School Map");
			primaryStage.getIcons().add(new Image("/br/com/senac/assets/image/user2.png"));
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}

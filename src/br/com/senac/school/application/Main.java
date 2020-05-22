package br.com.senac.school.application;

import java.io.IOException;
import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.senac.school.session.Session;
import br.com.senac.school.util.VIEWS_NAMES;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
	private static Logger logger = LogManager.getLogger(Main.class);

	@Override
	public void start(Stage primaryStage) {
		try {
			Session.data = LocalDateTime.now().plusHours(3).toString();

			FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEWS_NAMES.SPLASH.getName()));
			Parent pane = loader.load();
			Scene mainScene = new Scene(pane);
			primaryStage.setScene(mainScene);
			primaryStage.setTitle("School Map");
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image("/br/com/senac/assets/image/user2.png"));
			primaryStage.show();
			logger.info("Iniciando o programa");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}

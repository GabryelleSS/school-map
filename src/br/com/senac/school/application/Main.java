package br.com.senac.school.application;

import java.io.IOException;
import java.util.List;

import br.com.senac.school.dao.EscolaDao;
import br.com.senac.school.dao.EscolaDaoImpl;
import br.com.senac.school.model.EnderecoUsuario;
import br.com.senac.school.model.Escola;
import br.com.senac.school.session.Session;
import br.com.senac.school.util.VIEWS_NAMES;
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
			FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEWS_NAMES.SPLASH.getName()));
			Parent pane = loader.load();
			Scene mainScene = new Scene(pane);
			primaryStage.setScene(mainScene);
			primaryStage.setTitle("School Map");
			primaryStage.setResizable(false);
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

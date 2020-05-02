package br.com.senac.school.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;

public class SplashController implements Initializable {

	@FXML
	private ProgressBar progressBar;

	@FXML
	private Label loading;

	@FXML
	private AnchorPane root;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		startProgressBar();
	}

	private void startProgressBar() {

		Task<Void> tasks = progressTask();

		messageTask(tasks);

		progressBar.progressProperty().unbind();
		progressBar.progressProperty().bind(tasks.progressProperty());

		Thread thread = new Thread(tasks);
		thread.setDaemon(true);
		thread.start();
	}

	private void messageTask(Task<Void> tasks) {
		tasks.messageProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {

			loading.setText(newValue.concat("%"));

			if (newValue.equals("100")) {
				loadHome();
			}
		});
	}

	private Task<Void> progressTask() {
		return new Task<Void>() {

			@Override
			protected Void call() throws Exception {

				for (int i = 0; i <= 100; i++) {
					updateProgress(i, 100);
					updateMessage(String.valueOf(i));
					Thread.sleep(30);
				}
				return null;
			}
		};
	}

	private void loadHome() {
		try {
			AnchorPane parentContent = FXMLLoader.load(getClass().getResource(("/br/com/senac/school/view/Home.fxml")));
			root.getChildren().setAll(parentContent);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
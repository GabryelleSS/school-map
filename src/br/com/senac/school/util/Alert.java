package br.com.senac.school.util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class Alert {

	public static void show(String type, String mensage, StackPane pane) {
		JFXDialogLayout content = new JFXDialogLayout();
		content.setHeading(new Text(type));
		content.setBody(new Text(mensage));
		JFXDialog dialog = new JFXDialog(pane, content, JFXDialog.DialogTransition.CENTER);
		dialog.setPrefWidth(50);
		JFXButton button = new JFXButton("OK");
		button.setStyle("-fx-background-color: #4087fd; -fx-border-color:#4087fd; -fx-border-style:solid; -fx-text-fill: white;");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dialog.close();

			}
		});
		content.setActions(button);
		dialog.show();
	}

}

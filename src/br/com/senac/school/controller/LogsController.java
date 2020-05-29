package br.com.senac.school.controller;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import br.com.senac.school.log.Logs;
import br.com.senac.school.model.Log;
import br.com.senac.school.util.Alert;
import br.com.senac.school.util.DashboardPaneContent;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class LogsController implements Initializable {

	@FXML
	private JFXTreeTableView<Log> table;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		JFXTreeTableColumn<Log, String> id = new JFXTreeTableColumn<>("ID");
		id.setPrefWidth(150);
		id.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Log, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Log, String> param) {
				return param.getValue().getValue().getId();
			}
		});

		JFXTreeTableColumn<Log, String> data = new JFXTreeTableColumn<>("Data");
		data.setPrefWidth(198);
		data.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Log, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Log, String> param) {
						return param.getValue().getValue().getData();
					}
				});

		JFXTreeTableColumn<Log, String> evento = new JFXTreeTableColumn<>("Evento");
		evento.setPrefWidth(250);
		evento.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Log, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Log, String> param) {
						return param.getValue().getValue().getEvento();
					}
				});
		JFXTreeTableColumn<Log, String> ip = new JFXTreeTableColumn<>("IP");
		ip.setPrefWidth(250);
		ip.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Log, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Log, String> param) {
				return param.getValue().getValue().getIp();
			}
		});

		JFXTreeTableColumn<Log, String> level = new JFXTreeTableColumn<>("Nível");
		level.setPrefWidth(150);
		level.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Log, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Log, String> param) {
						return param.getValue().getValue().getLevel();
					}
				});

		ObservableList<Log> logs = FXCollections.observableArrayList();

		Collection<Log> list = Logs.findAll();

		list.forEach(x -> logs.add(x));

		final TreeItem<Log> root = new RecursiveTreeItem<Log>(logs, RecursiveTreeObject::getChildren);
		table.getColumns().setAll(id, data, evento, level, ip);
		table.setRoot(root);
		table.setShowRoot(false);

	}

	@FXML
	public void remove(ActionEvent event) {

		if (table.getSelectionModel().getSelectedItem() == null) {
			Alert.show("Nenhum LOG selecionado", "Por favor selecionar antes de remover", DashboardPaneContent.root);
		} else {

			JFXDialogLayout content = new JFXDialogLayout();
			content.setHeading(new Text("Essa ação não tem volta"));
			content.setBody(new Text("Você tem certeza que deseja excluir o LOG selecionado ?"));
			JFXDialog dialog = new JFXDialog(DashboardPaneContent.root, content, JFXDialog.DialogTransition.CENTER);
			dialog.setPrefWidth(50);

			JFXButton sim = new JFXButton("Sim");
			sim.setStyle(
					" -fx-background-color: #4087fd ;-fx-border-color: #4087fd; -fx-border-style:solid; -fx-text-fill: #fff;");

			sim.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					removeLog();
					table.getSelectionModel().clearSelection();
					dialog.close();
				}

			});
			JFXButton nao = new JFXButton("Não");
			nao.setStyle(
					" -fx-background-color: #9e9e9e ;-fx-border-color:#9e9e9e; -fx-border-style:solid; -fx-text-fill: #fff;");
			nao.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					dialog.close();
				}
			});
			content.setActions(sim, nao);
			dialog.show();

		}

	}

	private void removeLog() {
		TreeItem<Log> item = table.getSelectionModel().getSelectedItem();
		if (item != null) {
			Logs.remove(item.getValue().getId().get());
			table.getSelectionModel().getSelectedItem().getParent().getChildren().remove(item);
			table.refresh();
		}
	}

}

package br.com.senac.school.controller;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import br.com.senac.school.model.School;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class DashboardController implements Initializable {

	@FXML
	private VBox schoolItems;

	@FXML
	private JFXDrawer drawer;

	@FXML
	private JFXHamburger hamburger;

	private ObservableList<School> listOfSchools;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.submit(searchData);

		searchData.setOnSucceeded((event) -> {

			listOfSchools = FXCollections.observableArrayList(searchData.getValue());
			int size = listOfSchools.size();

			try {
				Node[] nodes = new Node[size];
				for (int i = 0; i < nodes.length; i++) {

					FXMLLoader loader = new FXMLLoader(
							getClass().getResource("/br/com/senac/school/view/SchoolItem.fxml"));
					SchoolItemController controller = new SchoolItemController();
					loader.setController(controller);
					nodes[i] = loader.load();
					schoolItems.getChildren().add(nodes[i]);
					controller.setTask(listOfSchools.get(i));
				}

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});

		menuHamburger();
		sidePane();
	}

	private void sidePane() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/senac/school/view/Sidepanel.fxml"));
			VBox box = loader.load();
			drawer.setSidePane(box);
		} catch (Exception e) {
			System.out.println("erro");
		}
	}

	private void menuHamburger() {
		HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
		transition.setRate(-1);
		hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
			transition.setRate(transition.getRate() * -1);
			transition.play();

			if (drawer.isShown()) {
				drawer.close();
			} else {
				drawer.open();
			}
		});
	}

	private final Task<List<School>> searchData = new Task<List<School>>() {

		@Override
		protected List<School> call() throws Exception {
			List<School> list = Arrays.asList(new School("oi"), new School("dsdsds"), new School("teste"));
			return list;
		}

	};

}

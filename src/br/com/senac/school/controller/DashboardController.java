package br.com.senac.school.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import br.com.senac.school.dao.EscolaDao;
import br.com.senac.school.dao.EscolaDaoImpl;
import br.com.senac.school.model.EnderecoUsuario;
import br.com.senac.school.model.Escola;
import br.com.senac.school.model.Usuario;
import br.com.senac.school.session.Session;
import br.com.senac.school.util.Alert;
import br.com.senac.school.util.DashboardPaneContent;
import br.com.senac.school.util.LoadViews;
import br.com.senac.school.util.VIEWS_NAMES;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class DashboardController implements Initializable {

	@FXML
	private JFXHamburger menuHamburguer;

	@FXML
	private JFXDrawer drawer;

	@FXML
	private VBox items;

	@FXML
	private StackPane content;

	@FXML
	private StackPane root;

	private ObservableList<Escola> listOfSchools;

	private static boolean searchWithoutReturn;
	private static boolean welcomeMessage = true;;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		loadSchools();
		DashboardPaneContent.pane = content;
		DashboardPaneContent.root = root;
	}

	private void loadSchools() {
		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.submit(searchData);

		searchData.setOnSucceeded((event) -> {

			listOfSchools = FXCollections.observableArrayList(searchData.getValue());
			int size = listOfSchools.size();

			if (searchWithoutReturn) {
				
				Alert.show("Nenhuma escola entrada", "Infelizmente não encontramos escolas próximas ao seu endereço,\n"
						+ "porem listamos algumas escolas que possam ser do seu interesse,\n"
						+ "você pode realizar buscas pelo nome no campo logo acima.  ", root);

			} else {
				
				welcomeMessage();

				try {

					boolean itsOdd = false;

					if (size % 2 != 0) {
						itsOdd = true;
					}

					for (int i = 0; i < size; i += 2) {

						if (itsOdd) {

							if (i == size - 1) {
								FXMLLoader loader = new FXMLLoader(
										getClass().getResource(VIEWS_NAMES.ITEM_DASHBOARD1.getName()));

								ItemDashboardController controller = new ItemDashboardController();
								loader.setController(controller);
								items.getChildren().add(loader.load());
								controller.setTaskView1(listOfSchools.get(i));
							} else {
								FXMLLoader loader = loadPane1();

								ItemDashboardController controller = new ItemDashboardController();
								loader.setController(controller);
								items.getChildren().add(loader.load());

								if (i == 0) {
									controller.setTaskView2(listOfSchools.get(i), listOfSchools.get(i + 1));

								} else {

									controller.setTaskView2(listOfSchools.get(i), listOfSchools.get(i + 1));
								}
							}

						} else {
							FXMLLoader loader = loadPane1();

							ItemDashboardController controller = new ItemDashboardController();
							loader.setController(controller);
							items.getChildren().add(loader.load());
							if (i == 0) {
								controller.setTaskView2(listOfSchools.get(i), listOfSchools.get(i + 1));

							} else {

								controller.setTaskView2(listOfSchools.get(i), listOfSchools.get(i + 1));
							}
						}
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
	}

	private void welcomeMessage() {
		if(welcomeMessage) {
			Alert.show("Seja bem-vindo!", "Bem-vindo ao School Map, listamos as escolas mais próximas do seu endereço,\n"
					+ "você pode procurar escolas pelo nome no campo logo acima.  ", root);
			welcomeMessage=false;
		}
	}

	private FXMLLoader loadPane1() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEWS_NAMES.ITEM_DASHBOARD2.getName()));
		return loader;
	}

	private final Task<List<Escola>> searchData = new Task<List<Escola>>() {

		@Override
		protected List<Escola> call() throws Exception {
			EnderecoUsuario endereco = Session.getUsuario().getEndereco();
			EscolaDao dao = new EscolaDaoImpl();

			List<Escola> list = dao.findByLatElong(endereco.getLatitude(), endereco.getLongitude());

			if (list.isEmpty()) {
				searchWithoutReturn = true;
			}
			return list;
		}

	};

	@FXML
	void configurations(MouseEvent event) {

	}

	@FXML
	void editProfile(MouseEvent event) {
		new LoadViews().load(content, VIEWS_NAMES.EDIT_PROFILE);

	}

	@FXML
	void home(MouseEvent event) {
		new LoadViews().load(root, VIEWS_NAMES.DASHBOARD);

	}

	@FXML
	void logout(MouseEvent event) {
		Session.removeUsuario();
		new LoadViews().load(root, VIEWS_NAMES.LOGIN);
	}

}

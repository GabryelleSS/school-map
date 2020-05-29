package br.com.senac.school.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.senac.school.dao.EscolaDao;
import br.com.senac.school.dao.EscolaDaoImpl;
import br.com.senac.school.log.Logs;
import br.com.senac.school.model.EnderecoUsuario;
import br.com.senac.school.model.Escola;
import br.com.senac.school.session.EscolasCache;
import br.com.senac.school.session.Session;
import br.com.senac.school.util.Alert;
import br.com.senac.school.util.DashboardPaneContent;
import br.com.senac.school.util.LoadViews;
import br.com.senac.school.util.VIEWS_NAMES;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class DashboardController implements Initializable {

	@FXML
	private Button btnSearch;

	@FXML
	private TextField fieldSearch;

	@FXML
	private VBox items;

	@FXML
	private StackPane content;

	@FXML
	private StackPane root;
	
	@FXML
	private Label labelDashboard;

	private ObservableList<Escola> listOfSchools;

	private static boolean searchWithoutReturn;
	private static boolean welcomeMessage = true;
	private static boolean searchActive = false;
	private static boolean searchNotResult = false;
	private static boolean favoritesActive = false;
	private static String searchField;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		if(favoritesActive) {
			labelDashboard.setText("Escolas marcadas como favorita:");
		}
		
		fieldSearch.setVisible(true);
		btnSearch.setVisible(true);
		loadSchools();
		DashboardPaneContent.pane = content;
		DashboardPaneContent.root = root;

		new Thread(() -> {

			EscolasCache.favorite(new EscolaDaoImpl().favorites(Session.getUsuario().getId()));

		}).start();
	}

	private void loadSchools() {
		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.submit(searchData);

		searchData.setOnSucceeded((event) -> {

			listOfSchools = FXCollections.observableArrayList(searchData.getValue());
			int size = listOfSchools.size();

			if (searchNotResult) {
				Alert.show("Nenhuma escola encontrada",
						"Infelizmente não encontramos nenhuma escola com esse nome ou tipo.", root);
				searchNotResult = false;
				searchActive = false;
			}
			if (searchWithoutReturn) {

				Alert.show("Nenhuma escola encontrada",
						"Infelizmente não encontramos escolas próximas ao seu endereço,\n"
								+ "porem você pode realizar buscas pelo nome da escola no campo logo acima.",
						root);

			} else {
				searchActive = false;
				favoritesActive = false;
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
		if (welcomeMessage) {
			Alert.show("Seja bem-vindo!",
					"Bem-vindo ao School Map, listamos as escolas mais próximas do seu endereço,\n"
							+ "você pode procurar escolas pelo nome no campo logo acima.  ",
					root);
			welcomeMessage = false;
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

			if (favoritesActive) {
				List<Escola> list = EscolasCache.favorites();

				if (list.isEmpty()) {
					Alert.show("Sem favoritos", "Você ainda não marcou nenhuma escola como favorita.", root);
				}
				return list;

			} else if (searchActive) {
				List<Escola> list = new EscolaDaoImpl().findByNameOrType(searchField);

				if (list.isEmpty()) {
					searchNotResult = true;
					list = EscolasCache.escolas();
				} else {
					EscolasCache.add(list);
				}
				return list;
			} else {
				if (!EscolasCache.escolas().isEmpty()) {
					return EscolasCache.escolas();
				} else {
					EscolaDao dao = new EscolaDaoImpl();

					List<Escola> list = dao.findByLatElong(endereco.getLatitude(), endereco.getLongitude());
					EscolasCache.add(list);

					if (list.isEmpty()) {
						searchWithoutReturn = true;
					}

					return list;
				}
			}
		}

	};

	@FXML
	void btnSearch(ActionEvent event) {

		if (fieldSearch.getText().trim().isEmpty()) {
			Alert.show("Campo obrigatório", "Por favor preencher o campo para realizar a pesquisa.", root);
		} else {
			searchField = fieldSearch.getText();
			searchActive = true;
			new LoadViews().load(root, VIEWS_NAMES.DASHBOARD);
		}

	}

	@FXML
	void favorites(MouseEvent event) {
		favoritesActive = true;
		new LoadViews().load(root, VIEWS_NAMES.DASHBOARD);
	}

	@FXML
	void editProfile(MouseEvent event) {
		fieldSearch.setVisible(false);
		btnSearch.setVisible(false);
		new LoadViews().load(content, VIEWS_NAMES.EDIT_PROFILE);

	}

	@FXML
	void home(MouseEvent event) {
		favoritesActive = false;
		fieldSearch.setVisible(true);
		btnSearch.setVisible(true);
		new LoadViews().load(root, VIEWS_NAMES.DASHBOARD);

	}

	@FXML
	void logs(MouseEvent event) {
		fieldSearch.setVisible(false);
		btnSearch.setVisible(false);

		new LoadViews().load(content, VIEWS_NAMES.LOGS);

	}

	@FXML
	void logout(MouseEvent event) {
		Logs.clear();
		Session.removeUsuario();
		EscolasCache.clean();
		new LoadViews().load(root, VIEWS_NAMES.LOGIN);
	}

}

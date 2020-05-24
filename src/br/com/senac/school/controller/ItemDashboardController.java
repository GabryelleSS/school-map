package br.com.senac.school.controller;

import java.io.IOException;

import br.com.senac.school.dao.EscolaDao;
import br.com.senac.school.model.Escola;
import br.com.senac.school.session.EscolasCache;
import br.com.senac.school.util.DashboardPaneContent;
import br.com.senac.school.util.VIEWS_NAMES;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class ItemDashboardController {

	@FXML
	private Label nomeCard1;

	@FXML
	private Label tipoCard1;

	@FXML
	private Label enderecoCard1;

	@FXML
	private Label bairroCard1;

	@FXML
	private Label telefoneCard1;

	@FXML
	private Label numeroCard1;

	@FXML
	private Label distritoCard1;

	@FXML
	private Label idCard1;

	@FXML
	private Label nomeCard2;

	@FXML
	private Label tipoCard2;

	@FXML
	private Label enderecoCard2;

	@FXML
	private Label bairroCard2;

	@FXML
	private Label telefoneCard2;

	@FXML
	private Label numeroCard2;

	@FXML
	private Label distritoCard2;

	@FXML
	private Label idCard2;

	@FXML
	private Label nomeCard3;

	@FXML
	private Label tipoCard3;

	@FXML
	private Label enderecoCard3;

	@FXML
	private Label bairroCard3;

	@FXML
	private Label telefoneCard3;

	@FXML
	private Label numeroCard3;

	@FXML
	private Label distritoCard3;

	@FXML
	private Label idCard3;

	EscolaDao dao;

	public void setTaskView1(Escola escola) {
		ContextMenu menu = new ContextMenu();
		loadCard3(escola, menu);
	}

	public void setTaskView2(Escola escola1, Escola escola2) {
		ContextMenu menu = new ContextMenu();
		loadCard1(escola1, menu);
		loadCard2(escola2, menu);

	}

	private void loadCard1(Escola escola, ContextMenu menu) {
		idCard1.setText(String.valueOf(escola.getId()));
		nomeCard1.setText(String.valueOf(escola.getNome()));
		bairroCard1.setText(String.valueOf(escola.getBairro()));
		distritoCard1.setText(String.valueOf(escola.getDistrito()));
		enderecoCard1.setText(String.valueOf(escola.getEndereco()));
		numeroCard1.setText(String.valueOf(escola.getNumero()));
		telefoneCard1.setText(String.valueOf(escola.getTelefone1()));
		tipoCard1.setText(String.valueOf(escola.getTipo()));

		idCard1.setContextMenu(menu);
		nomeCard1.setContextMenu(menu);
		bairroCard1.setContextMenu(menu);
		distritoCard1.setContextMenu(menu);
		enderecoCard1.setContextMenu(menu);
		numeroCard1.setContextMenu(menu);
		telefoneCard1.setContextMenu(menu);
		tipoCard1.setContextMenu(menu);
	}

	private void loadCard2(Escola escola, ContextMenu menu) {
		idCard2.setText(String.valueOf(escola.getId()));
		nomeCard2.setText(String.valueOf(escola.getNome()));
		bairroCard2.setText(String.valueOf(escola.getBairro()));
		distritoCard2.setText(String.valueOf(escola.getDistrito()));
		enderecoCard2.setText(String.valueOf(escola.getEndereco()));
		numeroCard2.setText(String.valueOf(escola.getNumero()));
		telefoneCard2.setText(String.valueOf(escola.getTelefone1()));
		tipoCard2.setText(String.valueOf(escola.getTipo()));

		idCard2.setContextMenu(menu);
		nomeCard2.setContextMenu(menu);
		bairroCard2.setContextMenu(menu);
		distritoCard2.setContextMenu(menu);
		enderecoCard2.setContextMenu(menu);
		numeroCard2.setContextMenu(menu);
		telefoneCard2.setContextMenu(menu);
		tipoCard2.setContextMenu(menu);
	}

	private void loadCard3(Escola escola, ContextMenu menu) {
		idCard3.setText(String.valueOf(escola.getId()));
		nomeCard3.setText(String.valueOf(escola.getNome()));
		bairroCard3.setText(String.valueOf(escola.getBairro()));
		distritoCard3.setText(String.valueOf(escola.getDistrito()));
		enderecoCard3.setText(String.valueOf(escola.getEndereco()));
		numeroCard3.setText(String.valueOf(escola.getNumero()));
		telefoneCard3.setText(String.valueOf(escola.getTelefone1()));
		tipoCard3.setText(String.valueOf(escola.getTipo()));

		idCard3.setContextMenu(menu);
		nomeCard3.setContextMenu(menu);
		bairroCard3.setContextMenu(menu);
		distritoCard3.setContextMenu(menu);
		enderecoCard3.setContextMenu(menu);
		numeroCard3.setContextMenu(menu);
		telefoneCard3.setContextMenu(menu);
		tipoCard3.setContextMenu(menu);
	}

	@FXML
	void card1(MouseEvent event) throws IOException {

		Integer id = Integer.valueOf(idCard1.getText());
		selectedItem(id);

	}

	@FXML
	void card2(MouseEvent event) {
		Integer id = Integer.valueOf(idCard2.getText());
		selectedItem(id);
	}

	@FXML
	void card3(MouseEvent event) {
		Integer id = Integer.valueOf(idCard3.getText());
		selectedItem(id);
	}

	private void selectedItem(int id) {

		Escola escola = EscolasCache.getEscola(id);

		try {
			loadItemSelected(escola);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadItemSelected(Escola escola) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEWS_NAMES.ITEM_SELECTED.getName()));

		ItemDashboardSelectedController controller = new ItemDashboardSelectedController();
		loader.setController(controller);

		DashboardPaneContent.pane.getChildren().add(loader.load());
		controller.setTaskView(escola);
	}

}

package br.com.senac.school.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.senac.school.dao.EscolaDao;
import br.com.senac.school.dao.EscolaDaoImpl;
import br.com.senac.school.model.Escola;
import br.com.senac.school.session.EscolasCache;
import br.com.senac.school.session.Session;
import br.com.senac.school.util.DashboardPaneContent;
import br.com.senac.school.util.LoadViews;
import br.com.senac.school.util.VIEWS_NAMES;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ItemDashboardSelectedController {

	@FXML
	private ImageView imageMark;

	@FXML
	private Label nome;

	@FXML
	private Label tipo;

	@FXML
	private Label endereco;

	@FXML
	private Label bairro;

	@FXML
	private Label telefone1;

	@FXML
	private Label numero;

	@FXML
	private Label codDistrito;

	@FXML
	private Label telefone2;

	@FXML
	private Label cep;

	@FXML
	private Label distrito;

	private int id = 0;
	private Escola escola;

	@FXML
	void btnBackItems(MouseEvent event) {
		new LoadViews().load(DashboardPaneContent.root, VIEWS_NAMES.DASHBOARD);
	}

	public void setTaskView(Escola escola) {
		ContextMenu menu = new ContextMenu();
		loadCard(escola, menu);

	}

	private void loadCard(Escola escola, ContextMenu menu) {
		id = escola.getId();
		this.escola = escola;

		if (EscolasCache.isFavorited(id)) {
			imageMark.setImage(new Image("/br/com/senac/assets/image/bookmark-selected.png"));
		}

		nome.setText(String.valueOf(escola.getNome()));
		bairro.setText(String.valueOf(escola.getBairro()));
		distrito.setText(String.valueOf(escola.getDistrito()));
		codDistrito.setText(String.valueOf(escola.getCod_distrito()));
		endereco.setText(String.valueOf(escola.getEndereco()));
		numero.setText(String.valueOf(escola.getNumero()));
		telefone1.setText(String.valueOf(escola.getTelefone1()));
		telefone2.setText(String.valueOf(escola.getTelefone2()));
		tipo.setText(String.valueOf(escola.getTipo()));
		cep.setText(String.valueOf(escola.getCep()));

		nome.setContextMenu(menu);
		bairro.setContextMenu(menu);
		distrito.setContextMenu(menu);
		codDistrito.setContextMenu(menu);
		endereco.setContextMenu(menu);
		numero.setContextMenu(menu);
		telefone1.setContextMenu(menu);
		telefone2.setContextMenu(menu);
		tipo.setContextMenu(menu);
		cep.setContextMenu(menu);

	}

	@FXML
	void btnFavorited(MouseEvent event) {
		ExecutorService service = Executors.newCachedThreadPool();

		EscolaDao dao = new EscolaDaoImpl();

		if (EscolasCache.isFavorited(id)) {
			EscolasCache.deslike(id);

			service.submit(() -> {
				dao.dislike(Session.getUsuario().getId(), id);
			});

			imageMark.setImage(new Image("/br/com/senac/assets/image/bookmark.png"));
		} else {
			EscolasCache.favorite(id, escola);
			
			service.submit(() -> {
				dao.favorited(Session.getUsuario().getId(), id);
			});
			
			imageMark.setImage(new Image("/br/com/senac/assets/image/bookmark-selected.png"));
		}

	}

}
package br.com.senac.school.controller;

import br.com.senac.school.util.VIEWS_NAMES;

public interface MenuCallback {

	void updateView(VIEWS_NAMES view);

	void updateViewContent(VIEWS_NAMES view);

}

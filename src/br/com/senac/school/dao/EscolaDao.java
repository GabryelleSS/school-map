package br.com.senac.school.dao;

import java.util.List;

import br.com.senac.school.model.Escola;

public interface EscolaDao {

	List<Escola> findByBairro(String bairro);

	List<Escola> findByNameOrType(String value);

	List<Escola> findByLatElong(double latitude, double longitude);

	Escola findById(int id);

	void openConnection();

	void favorited(int user, int escola);

	List<Escola> favorites(int user);

	void dislike(int user, int escola);

	void closeConnection();

}
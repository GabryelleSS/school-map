package br.com.senac.school.dao;

import java.util.List;

import br.com.senac.school.model.Escola;

public interface EscolaDao {

	List<Escola> findByBairro(String bairro);

	List<Escola> findByLatElong(double latitude,double longitude);

	Escola findById(int id);

	void openConnection();

	void closeConnection();
}
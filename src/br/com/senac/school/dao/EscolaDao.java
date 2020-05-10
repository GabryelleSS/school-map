package br.com.senac.school.dao;

import java.util.List;

import br.com.senac.school.model.Escola;

public interface EscolaDao {

	List<Escola> findByBairro(String bairro);

	List<Escola> findAll();

	void openConnection();

	void closeConnection();
}
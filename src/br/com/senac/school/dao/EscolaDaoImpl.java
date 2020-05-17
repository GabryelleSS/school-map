package br.com.senac.school.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.senac.school.model.Escola;

public class EscolaDaoImpl implements EscolaDao {

	private Connection conn;

	@Override
	public List<Escola> findByBairro(String bairro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Escola> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void openConnection() {
		conn = ConnectionFactory.getConnection();

	}

	@Override
	public void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

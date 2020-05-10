package br.com.senac.school.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.senac.school.model.Escola;

public class EscolaDaoImpl implements EscolaDao {

	private EntityManager manager;

	@Override
	public List<Escola> findByBairro(String bairro) {
		String sql = "SELECT e FROM Escola e "
				+ "LEFT JOIN FETCH e.distrito "
				+ "LEFT JOIN FETCH e.telefone "
				+ "LEFT JOIN FETCH e.endereco "
				+ "WHERE e.endereco.bairro = :bairro";

		try {
			openConnection();
			TypedQuery<Escola> query = manager.createQuery(sql, Escola.class);
			query.setParameter("bairro", bairro);
			return query.getResultList();
		} finally {
			closeConnection();
		}
	}

	@Override
	public List<Escola> findAll() {
		String sql = "SELECT e FROM Escola e "
				+ "LEFT JOIN FETCH e.distrito "
				+ "LEFT JOIN FETCH e.telefone "
				+ "LEFT JOIN FETCH e.endereco";

		try {
			openConnection();
			TypedQuery<Escola> query = manager.createQuery(sql, Escola.class);
			return query.getResultList();
		} finally {
			closeConnection();
		}
	}

	@Override
	public void openConnection() {
		manager = JPAUtil.getEntityManager();

	}

	@Override
	public void closeConnection() {
		if (manager != null) {
			manager.close();
		}
	}

}

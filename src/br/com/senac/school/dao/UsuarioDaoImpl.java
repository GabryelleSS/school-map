package br.com.senac.school.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.senac.school.model.Usuario;

public class UsuarioDaoImpl implements UsuarioDao {

	private EntityManager manager;

	@Override
	public Optional<Usuario> findByCpf(String cpf) {
		String sql = "SELECT u FROM Usuario u " + "join fetch u.telefones " + "join fetch u.endereco "
				+ "WHERE u.cpf = :cpf";
		try {
			openConnection();
			TypedQuery<Usuario> query = manager.createQuery(sql, Usuario.class);
			query.setParameter("cpf", cpf);
			Usuario usuario = query.getSingleResult();

			return Optional.ofNullable(usuario);

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeConnection();
		}
	}

	@Override
	public List<Usuario> findAll() {
		String sql = "SELECT u FROM Usuario u";

		try {
			openConnection();
			TypedQuery<Usuario> query = manager.createQuery(sql, Usuario.class);
			return query.getResultList();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeConnection();
		}
	}

	@Override
	public void save(Usuario usuario) {
		try {
			openConnection();
			manager.getTransaction().begin();
			manager.persist(usuario);
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
		} finally {
			closeConnection();
		}
	}

	@Override
	public void update(Usuario usuario) {
		try {
			openConnection();
			manager.getTransaction().begin();
			manager.merge(usuario);
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
		} finally {
			closeConnection();
		}
	}

	@Override
	public void delete(String cpf) {
		String sql = "UPDATE Usuario " + "SET ativo = false " + "WHERE cpf = :cpf";
		try {
			openConnection();
			manager.getTransaction().begin();
			Query query = manager.createQuery(sql);
			query.setParameter("cpf", cpf);
			query.executeUpdate();
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
		} finally {
			closeConnection();
		}
	}

	public void openConnection() {
		manager = JPAUtil.getEntityManager();
	}

	public void closeConnection() {
		if (manager != null) {
			manager.close();
		}

	}

	@Override
	public List<String> passwordLogin(String email) {
		String sql = "SELECT u.senha FROM Usuario u WHERE u.email = :email";
		try {
			openConnection();
			TypedQuery<String> query = manager.createQuery(sql, String.class);
			query.setParameter("email", email);
			List<String> passwordUsuario = query.getResultList();

			return passwordUsuario;

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeConnection();
		}
	}
}

package br.com.senac.school.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class JPAUtil {

	private static EntityManagerFactory factory;

	static {
		factory = Persistence.createEntityManagerFactory("school-map");
	}

	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}

}

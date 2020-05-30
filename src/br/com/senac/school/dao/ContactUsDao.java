package br.com.senac.school.dao;

import br.com.senac.school.model.ContactUs;

public interface ContactUsDao {

	void save(ContactUs contactUs);

	void openConnection();

	void closeConnection();

}

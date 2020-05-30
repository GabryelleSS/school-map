package br.com.senac.school.model;

public class ContactUs {

	private String nome;
	private String email;
	private String cpf;
	private String type;
	private String message;
	private String data;

	public ContactUs(String nome, String email, String cpf, String type, String message, String data) {
		this.nome = nome;
		this.email = email;
		this.cpf = cpf;
		this.type = type;
		this.message = message;
		this.data = data;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getCpf() {
		return cpf;
	}

	public String getType() {
		return type;
	}

	public String getMessage() {
		return message;
	}

	public String getData() {
		return data;
	}

}

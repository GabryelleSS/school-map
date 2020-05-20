package br.com.senac.school.model;

import java.time.LocalDate;

public class Usuario {

	private int id;

	private String nome;

	private String cpf;

	private String sexo;

	private LocalDate dataNascimento;

	private String estadoCivil;

	private String telefone;

	private String celular;

	private EnderecoUsuario endereco;

	private String email;
	private String senha;

	private boolean ativo;

	public Usuario() {
	}

	public Usuario(String nome, String cpf, String sexo, LocalDate dataNascimento, String estadoCivil, String telefone,
			String celular, EnderecoUsuario endereco, String email, String senha) {
		this.nome = nome;
		this.cpf = cpf;
		this.sexo = sexo;
		this.dataNascimento = dataNascimento;
		this.estadoCivil = estadoCivil;
		this.telefone = telefone;
		this.celular = celular;
		this.endereco = endereco;
		this.email = email;
		this.senha = senha;
		this.ativo = true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public EnderecoUsuario getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoUsuario endereco) {
		this.endereco = endereco;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	 

}

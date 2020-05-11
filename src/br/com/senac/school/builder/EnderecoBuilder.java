package br.com.senac.school.builder;

import br.com.senac.school.model.EnderecoUsuario;

public class EnderecoBuilder {

	private String endereco;
	private String bairro;
	private String complemento;
	private Integer numero;
	private String cep;
	private String cidade;
	private String estado;
	private String uf;

	public EnderecoBuilder comEndereco(String endereco) {
		this.endereco = endereco;
		return this;
	}

	public EnderecoBuilder comBairro(String bairro) {
		this.bairro = bairro;
		return this;
	}

	public EnderecoBuilder comComplemento(String complemento) {
		this.complemento = complemento;
		return this;
	}

	public EnderecoBuilder comNumero(String numero) {
		this.numero = Integer.valueOf(numero);
		return this;
	}

	public EnderecoBuilder comCep(String cep) {
		this.cep = cep;
		return this;
	}

	public EnderecoBuilder comEstado(String estado) {
		this.estado = estado;
		return this;
	}

	public EnderecoBuilder comUf(String uf) {
		this.uf = uf;
		return this;
	}

	public EnderecoBuilder comCidade(String cidade) {
		this.cidade = cidade;
		return this;
	}

	public EnderecoUsuario gera() {
		return new EnderecoUsuario(endereco, bairro, complemento, numero, cep, cidade, estado, uf);
	}

}

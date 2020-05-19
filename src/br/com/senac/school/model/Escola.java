package br.com.senac.school.model;

public class Escola {

	private int id;
	private String tipo;
	private String nome;
	private Boolean situacao;
	private String telefone1;
	private String telefone2;
	private String cod_distrito;
	private String distrito;
	private String endereco;
	private String bairro;
	private String cep;
	private Integer numero;
	private String latitude;
	private String longitude;

	public Escola(int id,String tipo, String nome, Boolean situacao, String telefone1, String telefone2, String cod_distrito,
			String distrito, String endereco, String bairro, String cep, Integer numero) {
		this.id = id;
		this.tipo = tipo;
		this.nome = nome;
		this.situacao = situacao;
		this.telefone1 = telefone1;
		this.telefone2 = telefone2;
		this.cod_distrito = cod_distrito;
		this.distrito = distrito;
		this.endereco = endereco;
		this.bairro = bairro;
		this.cep = cep;
		this.numero = numero;
	}
	
	public int getId() {
		return id;
	}

	public String getTipo() {
		return tipo;
	}

	public String getNome() {
		return nome;
	}

	public Boolean getSituacao() {
		return situacao;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public String getCod_distrito() {
		return cod_distrito;
	}

	public String getDistrito() {
		return distrito;
	}

	public String getEndereco() {
		return endereco;
	}

	public String getBairro() {
		return bairro;
	}

	public String getCep() {
		return cep;
	}

	public Integer getNumero() {
		return numero;
	}

}

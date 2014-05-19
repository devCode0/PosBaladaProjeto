package br.uniararas.mobile.baladanights.models;

import java.io.Serializable;

public class Usuario implements Serializable {
	
	private int idSessao;
	private String userName;
	private String nome;
	private String senha;
	
	public Usuario(){}
	
	public Usuario (String username, String senha) {
        this();
        this.userName = username;
        this.senha = senha;
    }

	public Usuario(int idSessao, String userName, String nome, String senha) {
		super();
		this.idSessao = idSessao;
		this.userName = userName;
		this.nome = nome;
		this.senha = senha;
	}

	public int getIdSessao() {
		return idSessao;
	}

	public void setIdSessao(int idSessao) {
		this.idSessao = idSessao;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	

}

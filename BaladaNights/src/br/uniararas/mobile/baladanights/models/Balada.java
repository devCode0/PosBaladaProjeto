package br.uniararas.mobile.baladanights.models;

import java.io.Serializable;

public class Balada implements Serializable{
	
	private String baladaId;
	private String nome;
	private String descricao;
	private String local;
	private String data;
	private String hora;
	private String open;
	
	public String getBaladaId() {
		return baladaId;
	}
	
	public void setBaladaId(String baladaId) {
		this.baladaId = baladaId;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getLocal() {
		return local;
	}
	
	public void setLocal(String local) {
		this.local = local;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public String getHora() {
		return hora;
	}
	
	public void setHora(String hora) {
		this.hora = hora;
	}
	
	public String getOpen() {
		return open;
	}
	
	public void setOpen(String open) {
		this.open = open;
	}
	
	
}

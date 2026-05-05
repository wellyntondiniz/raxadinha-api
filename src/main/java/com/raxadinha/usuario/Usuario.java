package com.raxadinha.usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")

public class Usuario {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
		
	@Column(name = "login")
	private String login;
		
	@Column(name = "senha")
	private String senha;
	
	public Integer getId() {
		return id;
	}
		
	public void setId(Integer id) {
		this.id = id;
	}
		
	public String getLogin() {
		return login;
	}
		
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getSenha() {
		return senha;
	}
		
	public void setSenha(String senha) {
		this.senha = senha;
	}
		
}


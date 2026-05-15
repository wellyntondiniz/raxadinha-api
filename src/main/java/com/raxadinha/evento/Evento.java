package com.raxadinha.evento;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "eventos")
public class Evento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "grupo_id")
	private Integer grupoId;
	
	@Column(name = "ativo")
	private Boolean ativo;
	
	@Column(name = "descricao")
	private String descricao;
	
	@Column(name = "data_inicio")
	private LocalDate data_inicio;
	
	@Column(name = "data_termino")
	private LocalDate data_termino;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Integer getGrupoId() {
		return grupoId;
	}
	
	public void setGrupoId(Integer grupoId) {
		this.grupoId = grupoId;
	}
	
	public Boolean getAtivo() {
		return ativo;
	}
	
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public LocalDate getDataInicio() {
		return data_inicio;
	}
	
	public void setDataInicio(LocalDate data_inicio) {
		this.data_inicio = data_inicio;
	}
	
	public LocalDate getDataTermino() {
		return data_termino;
	}
	
	public void setDataTermino(LocalDate data_termino) {
		this.data_termino = data_termino;
	}
}

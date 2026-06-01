package com.raxadinha.produto;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "produto")
@Data
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "unidade_de_medida", nullable = false)
    @Enumerated(EnumType.STRING)
    private UnidadeDeMedida unidadeDeMedida;

    @Column(name = "quantidade", nullable = false)
    private Double quantidade;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public UnidadeDeMedida getUnidadeDeMedida() {
		return unidadeDeMedida;
	}

	public void setUnidadeDeMedida(UnidadeDeMedida unidadeDeMedida) {
		this.unidadeDeMedida = unidadeDeMedida;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}
    
    
}

package com.raxadinha.listacompras;

import jakarta.persistence.*;

@Entity
@Table(name = "lista_compras")
public class ListaCompras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "evento_id", nullable = false)
    private Integer eventoId;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "criador_id")
    private Long criadorId;

    @Column(name = "ativo")
    private Boolean ativo = true;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getEventoId() { return eventoId; }
    public void setEventoId(Integer eventoId) { this.eventoId = eventoId; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Long getCriadorId() { return criadorId; }
    public void setCriadorId(Long criadorId) { this.criadorId = criadorId; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
}

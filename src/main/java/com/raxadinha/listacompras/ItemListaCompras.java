package com.raxadinha.listacompras;

import com.raxadinha.produto.UnidadeDeMedida;
import jakarta.persistence.*;

@Entity
@Table(name = "item_lista_compras")
public class ItemListaCompras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lista_id", nullable = false)
    private Long listaId;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "quantidade", nullable = false)
    private Double quantidade;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidade_de_medida", nullable = false)
    private UnidadeDeMedida unidadeDeMedida;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getListaId() { return listaId; }
    public void setListaId(Long listaId) { this.listaId = listaId; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Double getQuantidade() { return quantidade; }
    public void setQuantidade(Double quantidade) { this.quantidade = quantidade; }

    public UnidadeDeMedida getUnidadeDeMedida() { return unidadeDeMedida; }
    public void setUnidadeDeMedida(UnidadeDeMedida unidadeDeMedida) { this.unidadeDeMedida = unidadeDeMedida; }
}

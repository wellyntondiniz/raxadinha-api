package com.raxadinha.listacompras;

import jakarta.persistence.*;

@Entity
@Table(name = "preenchimento_item")
public class PreenchimentoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "usuario_nome")
    private String usuarioNome;

    @Column(name = "estabelecimento_nome")
    private String estabelecimentoNome;

    @Column(name = "preco")
    private Double preco;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getUsuarioNome() { return usuarioNome; }
    public void setUsuarioNome(String usuarioNome) { this.usuarioNome = usuarioNome; }

    public String getEstabelecimentoNome() { return estabelecimentoNome; }
    public void setEstabelecimentoNome(String estabelecimentoNome) { this.estabelecimentoNome = estabelecimentoNome; }

    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }
}

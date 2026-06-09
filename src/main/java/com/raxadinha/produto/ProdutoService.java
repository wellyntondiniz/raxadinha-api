package com.raxadinha.produto;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public List<Produto> listar() {
        return repository.findAll();
    }

    public Produto buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + id));
    }

    public Produto salvar(Produto produto) {
        return repository.save(produto);
    }

    public Produto atualizar(Long id, Produto dados) {
        Produto existente = buscarPorId(id);
        existente.setDescricao(dados.getDescricao());
        existente.setUnidadeDeMedida(dados.getUnidadeDeMedida());
        existente.setQuantidade(dados.getQuantidade());
        return repository.save(existente);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}

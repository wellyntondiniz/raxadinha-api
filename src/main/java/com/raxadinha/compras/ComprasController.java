package com.raxadinha.compras;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compras")
@CrossOrigin(origins = "*")
public class ComprasController {

    @Autowired
    private ComprasRepository repository;

    @GetMapping
    public List<Compras> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Compras salvar(@RequestBody Compras compra) {
        return repository.save(compra);
    }

    @PutMapping("/{id}")
public Compras atualizar(
        @PathVariable Long id,
        @RequestBody Compras compra) {

    Compras existente = repository.findById(id).orElseThrow();

    existente.setNome(compra.getNome());
    existente.setDescricao(compra.getDescricao());
    existente.setAtivo(compra.getAtivo());

    return repository.save(existente);
}

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
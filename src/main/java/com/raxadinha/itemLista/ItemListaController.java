package com.raxadinha.itemLista;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itemlista")
@CrossOrigin(origins = "*")
public class ItemListaController {

    @Autowired
    private ItemListaRepository repository;

    @GetMapping("/{compraId}")
public List<ItemLista> listar(@PathVariable Long compraId) {
    return repository.findByCompraId(compraId);
}
    @PostMapping
    public ItemLista salvar(@RequestBody ItemLista item) {
        return repository.save(item);
    }

    @PutMapping("/{id}")
public ItemLista atualizar(
        @PathVariable Long id,
        @RequestBody ItemLista item) {

    ItemLista existente = repository.findById(id).orElseThrow();

    existente.setNomeProduto(item.getNomeProduto());
    existente.setQuantidade(item.getQuantidade());
    existente.setComprado(item.getComprado());
    existente.setCompra(item.getCompra());

    return repository.save(existente);
}

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {

        repository.deleteById(id);

    }

}
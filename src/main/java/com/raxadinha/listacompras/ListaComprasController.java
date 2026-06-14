package com.raxadinha.listacompras;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lista-compras")
@CrossOrigin(origins = "*")
public class ListaComprasController {

    private final ListaComprasService service;

    public ListaComprasController(ListaComprasService service) {
        this.service = service;
    }

    @GetMapping
    public List<ListaCompras> listarTodas() {
        return service.listarTodas();
    }

    @GetMapping("/evento/{eventoId}")
    public List<ListaCompras> listarPorEvento(@PathVariable Integer eventoId) {
        return service.listarPorEvento(eventoId);
    }

    @GetMapping("/{id}")
    public ListaCompras buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public ListaCompras criar(@RequestBody ListaCompras lista) {
        return service.criar(lista);
    }

    @PutMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        service.desativar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{listaId}/itens")
    public List<ItemListaCompras> listarItens(@PathVariable Long listaId) {
        return service.listarItens(listaId);
    }

    @PostMapping("/{listaId}/itens")
    public ItemListaCompras adicionarItem(@PathVariable Long listaId,
                                          @RequestBody ItemListaCompras item) {
        return service.adicionarItem(listaId, item);
    }

    @PutMapping("/{listaId}/itens/{itemId}")
    public ItemListaCompras atualizarItem(@PathVariable Long listaId,
                                          @PathVariable Long itemId,
                                          @RequestBody ItemListaCompras item) {
        return service.atualizarItem(listaId, itemId, item);
    }

    @DeleteMapping("/{listaId}/itens/{itemId}")
    public ResponseEntity<Void> removerItem(@PathVariable Long listaId,
                                             @PathVariable Long itemId) {
        service.removerItem(listaId, itemId);
        return ResponseEntity.noContent().build();
    }
}

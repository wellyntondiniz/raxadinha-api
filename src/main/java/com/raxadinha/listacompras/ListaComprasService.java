package com.raxadinha.listacompras;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListaComprasService {

    private final ListaComprasRepository listaRepository;
    private final ItemListaComprasRepository itemRepository;

    public ListaComprasService(ListaComprasRepository listaRepository,
                               ItemListaComprasRepository itemRepository) {
        this.listaRepository = listaRepository;
        this.itemRepository = itemRepository;
    }

    public List<ListaCompras> listarTodas() {
        return listaRepository.findAllByAtivo(true);
    }

    public List<ListaCompras> listarPorEvento(Integer eventoId) {
        return listaRepository.findAllByEventoIdAndAtivo(eventoId, true);
    }

    public ListaCompras buscarPorId(Long id) {
        return listaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lista não encontrada: " + id));
    }

    public ListaCompras criar(ListaCompras lista) {
        if (lista.getNome() == null || lista.getNome().trim().isEmpty()) {
            throw new RuntimeException("Nome da lista é obrigatório");
        }
        if (lista.getEventoId() == null) {
            throw new RuntimeException("Evento é obrigatório");
        }
        lista.setAtivo(true);
        return listaRepository.save(lista);
    }

    public void desativar(Long id) {
        ListaCompras lista = buscarPorId(id);
        lista.setAtivo(false);
        listaRepository.save(lista);
    }

    public List<ItemListaCompras> listarItens(Long listaId) {
        buscarPorId(listaId);
        return itemRepository.findAllByListaId(listaId);
    }

    public ItemListaCompras adicionarItem(Long listaId, ItemListaCompras item) {
        buscarPorId(listaId);
        if (item.getDescricao() == null || item.getDescricao().trim().isEmpty()) {
            throw new RuntimeException("Descrição do item é obrigatória");
        }
        item.setListaId(listaId);
        return itemRepository.save(item);
    }

    public ItemListaCompras atualizarItem(Long listaId, Long itemId, ItemListaCompras dados) {
        ItemListaCompras item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado: " + itemId));
        if (!item.getListaId().equals(listaId)) {
            throw new RuntimeException("Item não pertence a esta lista");
        }
        item.setDescricao(dados.getDescricao());
        item.setQuantidade(dados.getQuantidade());
        item.setUnidadeDeMedida(dados.getUnidadeDeMedida());
        return itemRepository.save(item);
    }

    public void removerItem(Long listaId, Long itemId) {
        ItemListaCompras item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado: " + itemId));
        if (!item.getListaId().equals(listaId)) {
            throw new RuntimeException("Item não pertence a esta lista");
        }
        itemRepository.deleteById(itemId);
    }
}

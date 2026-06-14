package com.raxadinha.listacompras;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PreenchimentoService {

    private final PreenchimentoItemRepository repository;
    private final ItemListaComprasRepository itemRepository;

    public PreenchimentoService(PreenchimentoItemRepository repository,
                                ItemListaComprasRepository itemRepository) {
        this.repository = repository;
        this.itemRepository = itemRepository;
    }

    public List<PreenchimentoItem> listarPorLista(Long listaId) {
        List<ItemListaCompras> itens = itemRepository.findAllByListaId(listaId);
        List<Long> itemIds = itens.stream().map(ItemListaCompras::getId).toList();
        return repository.findAllByItemIdIn(itemIds);
    }

    public List<PreenchimentoItem> salvarLote(List<PreenchimentoItem> preenchimentos) {
        for (PreenchimentoItem p : preenchimentos) {
            Optional<PreenchimentoItem> existente = repository.findByItemIdAndUsuarioIdAndEstabelecimentoNome(
                p.getItemId(), p.getUsuarioId(), p.getEstabelecimentoNome()
            );
            existente.ifPresent(e -> p.setId(e.getId()));
        }
        return repository.saveAll(preenchimentos);
    }
}

package com.raxadinha.listacompras;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PreenchimentoItemRepository extends JpaRepository<PreenchimentoItem, Long> {
    List<PreenchimentoItem> findAllByItemId(Long itemId);
    List<PreenchimentoItem> findAllByItemIdIn(List<Long> itemIds);
    Optional<PreenchimentoItem> findByItemIdAndUsuarioIdAndEstabelecimentoNome(
        Long itemId, Long usuarioId, String estabelecimentoNome
    );
}

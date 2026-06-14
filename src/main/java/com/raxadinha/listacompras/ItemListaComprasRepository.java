package com.raxadinha.listacompras;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemListaComprasRepository extends JpaRepository<ItemListaCompras, Long> {
    List<ItemListaCompras> findAllByListaId(Long listaId);
}

package com.raxadinha.listacompras;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListaComprasRepository extends JpaRepository<ListaCompras, Long> {
    List<ListaCompras> findAllByAtivo(Boolean ativo);
    List<ListaCompras> findAllByEventoIdAndAtivo(Integer eventoId, Boolean ativo);
}

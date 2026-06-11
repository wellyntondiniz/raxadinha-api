package com.raxadinha.evento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    java.util.Optional<Evento> findFirstByNome(String nome);
}

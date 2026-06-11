package com.raxadinha.evento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer>{
	
	public List<Evento> findAllByAtivo(Boolean ativo);
	
	long countByAtivo(boolean ativo);
	
}

package com.raxadinha.evento;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventoService {
	
	@Autowired
	private EventoRepository eventoRepository;
	
	public List<Evento> getEventos() {
		return eventoRepository.findAllByAtivo(true);
	}
	
	public Evento deletar(Integer id) {
		Evento evento = eventoRepository.findById(id).get();
		evento.setAtivo(false);
		return eventoRepository.save(evento);
	}
	
}

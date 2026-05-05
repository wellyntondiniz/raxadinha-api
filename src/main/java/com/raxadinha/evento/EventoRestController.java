package com.raxadinha.evento;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/eventos")
@CrossOrigin
public class EventoRestController {
	
	@Autowired
	EventoService eventoService;
	
	@GetMapping
	public List<Evento> getEventos() {
		return eventoService.getEventos();
	}
	
	@PutMapping("{id")
	public Evento deletar(Integer id) {
		return eventoService.deletar(id);
	}

}

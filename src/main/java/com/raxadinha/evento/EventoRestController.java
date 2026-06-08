package com.raxadinha.evento;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.raxadinha.produto.Produto;

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
	
	@PutMapping("/{id}")
	public Evento deletar(Integer id) {
		return eventoService.deletar(id);
	}
	@GetMapping("/{id}")
	public Evento getEventoById(@PathVariable Integer id) {
		return eventoService.getEventoById(id);
	}
	
	@PutMapping("/{id}/desativar")
	public Evento desativar(@PathVariable Integer id) {
	    return eventoService.deletar(id);
	}
	
	@PostMapping
    public Evento salvar(@RequestBody Evento evento) {
        return eventoService.salvar(evento);
    }
	
	@PutMapping("/{id}")
    public Evento updateEvento(@PathVariable Integer id, @RequestBody Evento evento) {
        return eventoService.updateEvento(id, evento);
    }

}

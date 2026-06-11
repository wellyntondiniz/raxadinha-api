package com.raxadinha.evento;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventoService {
	
	@Autowired
	private EventoRepository eventoRepository;
	
	public void validar(Evento evento) {

	    if (evento.getNome() == null || evento.getNome().trim().isEmpty()) {
	        throw new RuntimeException("Nome é obrigatório");
	    }

	    if (evento.getDataTermino() != null && evento.getDataInicio() == null) {
	        throw new RuntimeException("Data de término exige data de início");
	    }

	    if (evento.getDataInicio() != null && evento.getDataTermino() != null) {
	        if (evento.getDataTermino().isBefore(evento.getDataInicio())) {
	            throw new RuntimeException("Data de término inválida");
	        }
	    }
	}
	
	public List<Evento> getEventos() {
		return eventoRepository.findAllByAtivo(true);
	}
	
	public Long getQuantidadeAtivos() {
	    return eventoRepository.countByAtivo(true);
	}
	
	public Evento getEventoById(Integer id) {
		Evento evento = eventoRepository.findById(id).get();
		return evento;
    }
	
	public Evento deletar(Integer id) {
		Evento evento = getEventoById(id);
		evento.setAtivo(false);
		return eventoRepository.save(evento);
	}
	
	public Evento salvar(Evento evento) {
		validar(evento);
		return eventoRepository.save(evento);
	}
	
	public Evento updateEvento(Integer id, Evento dados) {
		validar(dados);
		Evento evento = getEventoById(id);
        evento.setNome(dados.getNome());
        evento.setDescricao(dados.getDescricao());
        evento.setDataInicio(dados.getDataInicio());
        evento.setDataTermino(dados.getDataTermino());
        return eventoRepository.save(evento);
	}
	
}

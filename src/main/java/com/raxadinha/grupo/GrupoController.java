package com.raxadinha.grupo;

import com.raxadinha.evento.Evento;
import com.raxadinha.usuario.Usuario;
import com.raxadinha.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@RestController
@RequestMapping("/grupos")
@CrossOrigin(origins = "*") // Permite chamadas do Expo Snack/Mobile
public class GrupoController {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public Grupo criarGrupo(@RequestBody Grupo grupo) {
        return grupoRepository.save(grupo);
    }
    
    @GetMapping
    public List<Grupo> listarGrupos() {
        return grupoRepository.findAll();
    }

    // Endpoint para adicionar um usuário existente ao grupo pelo NOME
    @PostMapping("/{grupoId}/usuarios/nome/{usuarioNome}")
    public Grupo adicionarUsuarioAoGrupoPorNome(@PathVariable Long grupoId, @PathVariable String usuarioNome) {
        Optional<Grupo> grupoOpt = grupoRepository.findById(grupoId);
        Optional<Usuario> usuarioOpt = usuarioRepository.findFirstByNome(usuarioNome);

        if (grupoOpt.isPresent() && usuarioOpt.isPresent()) {
            Grupo grupo = grupoOpt.get();
            Usuario usuario = usuarioOpt.get();
            
            if (grupo.getUsuarios() == null) {
                grupo.setUsuarios(new ArrayList<>());
            }
            if (!grupo.getUsuarios().contains(usuario)) {
                grupo.getUsuarios().add(usuario);
                return grupoRepository.save(grupo);
            }
            return grupo; // Já estava no grupo
        }
        throw new RuntimeException("Grupo ou Usuário não encontrado");
    }

    @Autowired
    private com.raxadinha.evento.EventoRepository eventoRepository;

    // Endpoint para vincular um evento existente a este grupo pelo NOME
    @PutMapping("/{grupoId}/eventos/nome/{eventoNome}")
    public Grupo vincularEventoExistentePorNome(@PathVariable Long grupoId, @PathVariable String eventoNome) {
        Optional<Grupo> grupoOpt = grupoRepository.findById(grupoId);
        Optional<Evento> eventoOpt = eventoRepository.findFirstByNome(eventoNome);

        if (grupoOpt.isPresent() && eventoOpt.isPresent()) {
            Grupo grupo = grupoOpt.get();
            Evento evento = eventoOpt.get();
            
            //evento.setGrupo(grupo); // Muda o evento para este grupo
            eventoRepository.save(evento);
            
            return grupoRepository.findById(grupoId).get();
        }
        throw new RuntimeException("Grupo ou Evento não encontrado");
    }
}

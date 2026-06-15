package com.raxadinha.grupo;

import com.raxadinha.evento.Evento;
import com.raxadinha.evento.EventoRepository;
import com.raxadinha.grupo.dto.AdicionarUsuarioRequest;
import com.raxadinha.grupo.dto.GrupoListResponse;
import com.raxadinha.grupo.dto.GrupoRequest;
import com.raxadinha.grupo.dto.GrupoResponse;
import com.raxadinha.grupo.dto.MensagemRequest;
import com.raxadinha.grupo.dto.MensagemResponse;
import com.raxadinha.usuario.Usuario;
import com.raxadinha.usuario.UsuarioRepository;
import com.raxadinha.usuario.dto.UsuarioResponse;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/grupos", "/groups"})
@CrossOrigin(origins = "*") // Permite chamadas do Expo Snack/Mobile
public class GrupoController {

    private final GrupoService grupoService;
    private final GrupoRepository grupoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;

    public GrupoController(
            GrupoService grupoService,
            GrupoRepository grupoRepository,
            UsuarioRepository usuarioRepository,
            EventoRepository eventoRepository) {
        this.grupoService = grupoService;
        this.grupoRepository = grupoRepository;
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
    }

    // 1. Criar grupo
    @PostMapping
    public ResponseEntity<GrupoListResponse> criarGrupo(@Valid @RequestBody GrupoRequest req) {
        GrupoListResponse res = grupoService.criarGrupo(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    // 2. Editar grupo
    @PutMapping("/{id}")
    public ResponseEntity<GrupoResponse> editarGrupo(@PathVariable Long id, @Valid @RequestBody GrupoRequest req) {
        GrupoResponse res = grupoService.editarGrupo(id, req);
        return ResponseEntity.ok(res);
    }

    // 3. Buscar grupo por ID
    @GetMapping("/{id}")
    public ResponseEntity<GrupoResponse> buscarPorId(@PathVariable Long id) {
        GrupoResponse res = grupoService.buscarPorId(id);
        return ResponseEntity.ok(res);
    }

    // 4. Listar grupos
    @GetMapping
    public ResponseEntity<List<GrupoListResponse>> listarGrupos() {
        List<GrupoListResponse> res = grupoService.listarGrupos();
        return ResponseEntity.ok(res);
    }

    // 5. Buscar usuários para adicionar ao grupo
    @GetMapping("/users/search")
    public ResponseEntity<List<UsuarioResponse>> buscarUsuariosParaAdicionar(
            @RequestParam(value = "query", required = false) String query) {
        List<UsuarioResponse> res = grupoService.buscarUsuariosParaAdicionar(query);
        return ResponseEntity.ok(res);
    }

    // 6. Remover usuário de um grupo
    @DeleteMapping("/{groupId}/users/{userId}")
    public ResponseEntity<Void> removerUsuarioDoGrupo(@PathVariable Long groupId, @PathVariable Long userId) {
        grupoService.removerUsuarioDoGrupo(groupId, userId);
        return ResponseEntity.noContent().build();
    }

    // 7. Adicionar usuário em um grupo
    @PostMapping("/{groupId}/users")
    public ResponseEntity<GrupoResponse> adicionarUsuarioAoGrupo(
            @PathVariable Long groupId,
            @Valid @RequestBody AdicionarUsuarioRequest req) {
        GrupoResponse res = grupoService.adicionarUsuarioAoGrupo(groupId, req.userId());
        return ResponseEntity.ok(res);
    }

    // 8. Excluir grupo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirGrupo(@PathVariable Long id) {
        grupoService.excluirGrupo(id);
        return ResponseEntity.noContent().build();
    }

    // 9. Listar mensagens do chat do grupo
    @GetMapping("/{groupId}/messages")
    public ResponseEntity<List<MensagemResponse>> listarMensagens(@PathVariable Long groupId) {
        List<MensagemResponse> res = grupoService.listarMensagens(groupId);
        return ResponseEntity.ok(res);
    }

    // 10. Enviar mensagem no chat do grupo
    @PostMapping("/{groupId}/messages")
    public ResponseEntity<MensagemResponse> enviarMensagem(
            @PathVariable Long groupId,
            @Valid @RequestBody MensagemRequest req) {
        MensagemResponse res = grupoService.enviarMensagem(groupId, req);
        return ResponseEntity.ok(res);
    }


    // Endpoint antigo para adicionar um usuário existente ao grupo pelo NOME
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

<<<<<<< Updated upstream
    // Endpoint antigo para vincular um evento existente a este grupo pelo NOME
=======
}

    @Autowired
    private com.raxadinha.evento.EventoRepository eventoRepository;

    // Endpoint para vincular um evento existente a este grupo pelo NOME
>>>>>>> Stashed changes
    @PutMapping("/{grupoId}/eventos/nome/{eventoNome}")
    public Grupo vincularEventoExistentePorNome(@PathVariable Long grupoId, @PathVariable String eventoNome) {
        Optional<Grupo> grupoOpt = grupoRepository.findById(grupoId);
        Optional<Evento> eventoOpt = eventoRepository.findFirstByNome(eventoNome);

        if (grupoOpt.isPresent() && eventoOpt.isPresent()) {
            Grupo grupo = grupoOpt.get();
            Evento evento = eventoOpt.get();
            
            evento.setGrupoId(grupo.getId().intValue()); // Ajustado para setGrupoId compilável
            eventoRepository.save(evento);
            
            return grupoRepository.findById(grupoId).get();
        }
        throw new RuntimeException("Grupo ou Evento não encontrado");
    }

    @PutMapping("/{id}")
    public Grupo atualizar(
            @PathVariable Long id,
            @RequestBody Grupo grupo) {

        Grupo existente =
            grupoRepository.findById(id)
            .orElseThrow();

        existente.setNome(grupo.getNome());

        existente.setDescricao(
            grupo.getDescricao()
        );

        return grupoRepository.save(
            existente
        );
    }

    @DeleteMapping("/{id}")
    public void excluir(
            @PathVariable Long id
    ) {


        grupoRepository.deleteById(id);

}

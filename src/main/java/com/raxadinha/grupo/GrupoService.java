package com.raxadinha.grupo;

import com.raxadinha.grupo.dto.GrupoRequest;
import com.raxadinha.grupo.dto.GrupoResponse;
import com.raxadinha.grupo.dto.GrupoListResponse;
import com.raxadinha.grupo.dto.MensagemRequest;
import com.raxadinha.grupo.dto.MensagemResponse;
import com.raxadinha.usuario.Usuario;
import com.raxadinha.usuario.UsuarioRepository;
import com.raxadinha.usuario.dto.UsuarioResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Service
public class GrupoService {

    private final GrupoRepository grupoRepository;
    private final UsuarioRepository usuarioRepository;
    private final MensagemRepository mensagemRepository;

    public GrupoService(
            GrupoRepository grupoRepository, 
            UsuarioRepository usuarioRepository,
            MensagemRepository mensagemRepository) {
        this.grupoRepository = grupoRepository;
        this.usuarioRepository = usuarioRepository;
        this.mensagemRepository = mensagemRepository;
    }

    public GrupoListResponse criarGrupo(GrupoRequest req) {
        if (req.name() == null || req.name().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O nome do grupo é obrigatório");
        }

        Grupo grupo = new Grupo();
        grupo.setNome(req.name());
        grupo.setDescricao(req.description());

        List<Usuario> usuarios = new ArrayList<>();
        if (req.userIds() != null) {
            for (Long userId : req.userIds()) {
                Usuario usuario = usuarioRepository.findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado: " + userId));
                if (!usuarios.contains(usuario)) {
                    usuarios.add(usuario);
                }
            }
        }
        grupo.setUsuarios(usuarios);

        Grupo salvo = grupoRepository.save(grupo);
        return GrupoListResponse.de(salvo);
    }

    public GrupoResponse editarGrupo(Long id, GrupoRequest req) {
        if (req.name() == null || req.name().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O nome do grupo é obrigatório");
        }

        Grupo grupo = grupoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Grupo não encontrado: " + id));

        grupo.setNome(req.name());
        grupo.setDescricao(req.description());

        List<Usuario> usuarios = new ArrayList<>();
        if (req.userIds() != null) {
            for (Long userId : req.userIds()) {
                Usuario usuario = usuarioRepository.findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado: " + userId));
                if (!usuarios.contains(usuario)) {
                    usuarios.add(usuario);
                }
            }
        }
        grupo.setUsuarios(usuarios);

        Grupo salvo = grupoRepository.save(grupo);
        return GrupoResponse.de(salvo);
    }

    public GrupoResponse buscarPorId(Long id) {
        Grupo grupo = grupoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Grupo não encontrado: " + id));
        return GrupoResponse.de(grupo);
    }

    public List<GrupoListResponse> listarGrupos() {
        return grupoRepository.findAll().stream()
            .map(GrupoListResponse::de)
            .toList();
    }

    public List<UsuarioResponse> buscarUsuariosParaAdicionar(String query) {
        List<Usuario> usuarios;
        if (query == null || query.isBlank()) {
            usuarios = usuarioRepository.findAll();
        } else {
            usuarios = usuarioRepository.findByNomeContainingIgnoreCase(query);
        }
        return usuarios.stream()
            .map(UsuarioResponse::de)
            .toList();
    }

    public GrupoResponse adicionarUsuarioAoGrupo(Long grupoId, Long usuarioId) {
        Grupo grupo = grupoRepository.findById(grupoId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Grupo não encontrado: " + grupoId));

        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado: " + usuarioId));

        if (grupo.getUsuarios() == null) {
            grupo.setUsuarios(new ArrayList<>());
        }

        if (!grupo.getUsuarios().contains(usuario)) {
            grupo.getUsuarios().add(usuario);
            grupo = grupoRepository.save(grupo);
        }

        return GrupoResponse.de(grupo);
    }

    public void removerUsuarioDoGrupo(Long grupoId, Long usuarioId) {
        Grupo grupo = grupoRepository.findById(grupoId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Grupo não encontrado: " + grupoId));

        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado: " + usuarioId));

        if (grupo.getUsuarios() != null && grupo.getUsuarios().contains(usuario)) {
            grupo.getUsuarios().remove(usuario);
            grupoRepository.save(grupo);
        }
    }

    public void excluirGrupo(Long id) {
        if (!grupoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Grupo não encontrado: " + id);
        }
        // Desvincular eventos para evitar erro de FK
        grupoRepository.desvincularEventosDoGrupo(id.intValue());
        grupoRepository.deleteById(id);
    }

    public List<MensagemResponse> listarMensagens(Long grupoId) {
        if (!grupoRepository.existsById(grupoId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Grupo não encontrado: " + grupoId);
        }
        return mensagemRepository.findByGrupoIdOrderByDataEnvioAsc(grupoId).stream()
            .map(MensagemResponse::de)
            .toList();
    }

    public MensagemResponse enviarMensagem(Long grupoId, MensagemRequest req) {
        Grupo grupo = grupoRepository.findById(grupoId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Grupo não encontrado: " + grupoId));

        Usuario usuario = usuarioRepository.findById(req.userId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado: " + req.userId()));

        Mensagem msg = new Mensagem();
        msg.setGrupo(grupo);
        msg.setUsuario(usuario);
        msg.setConteudo(req.content());
        msg.setDataEnvio(LocalDateTime.now());

        Mensagem salva = mensagemRepository.save(msg);
        return MensagemResponse.de(salva);
    }
}

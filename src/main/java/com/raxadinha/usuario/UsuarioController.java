package com.raxadinha.usuario;

import com.raxadinha.usuario.dto.AtualizarRequest;
import com.raxadinha.usuario.dto.CadastroRequest;
import com.raxadinha.usuario.dto.LoginRequest;
import com.raxadinha.usuario.dto.UsuarioResponse;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    public List<UsuarioResponse> listar() {
        return service.listar().stream().map(UsuarioResponse::de).toList();
    }

    @GetMapping("/{id}")
    public UsuarioResponse buscarPorId(@PathVariable Long id) {
        return UsuarioResponse.de(service.buscarPorId(id));
    }

    // Verifica disponibilidade do nome de usuário (usado pela tela de cadastro)
    @GetMapping("/nome-disponivel")
    public java.util.Map<String, Boolean> nomeDisponivel(@RequestParam String nome) {
        return java.util.Map.of("disponivel", service.nomeDisponivel(nome));
    }

    // RF01 - Cadastro
    @PostMapping
    public ResponseEntity<UsuarioResponse> cadastrar(@Valid @RequestBody CadastroRequest req) {
        Usuario usuario = new Usuario();
        usuario.setNome(req.nome());
        usuario.setEmail(req.email());
        usuario.setSenha(req.senha());
        Usuario salvo = service.salvar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioResponse.de(salvo));
    }

    // RF02 - Login
    @PostMapping("/login")
    public LoginResposta login(@Valid @RequestBody LoginRequest req) {
        return service.login(req.email(), req.senha());
    }

    // RF03 - Editar
    @PutMapping("/{id}")
    public UsuarioResponse atualizar(@PathVariable Long id,
                                     @RequestHeader(value = "X-Usuario-Id", required = false) Long solicitanteId,
                                     @Valid @RequestBody AtualizarRequest req) {
        Usuario dados = new Usuario();
        dados.setNome(req.nome());
        dados.setEmail(req.email());
        dados.setSenha(req.senha());
        return UsuarioResponse.de(service.atualizar(id, dados, solicitanteId));
    }

    // RF04 - Excluir
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id,
                                        @RequestHeader(value = "X-Usuario-Id", required = false) Long solicitanteId) {
        service.excluir(id, solicitanteId);
        return ResponseEntity.noContent().build();
    }
}

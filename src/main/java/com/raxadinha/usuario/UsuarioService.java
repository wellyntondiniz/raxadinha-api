package com.raxadinha.usuario;

import com.raxadinha.usuario.exception.CredenciaisInvalidasException;
import com.raxadinha.usuario.exception.EmailJaCadastradoException;
import com.raxadinha.usuario.exception.RecursoNaoEncontradoException;
import com.raxadinha.usuario.exception.RegraNegocioException;
import com.raxadinha.usuario.dto.UsuarioResponse;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private static final int TAMANHO_MINIMO_SENHA = 5;

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<Usuario> listar() {
        return repository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado: " + id));
    }


    public Usuario salvar(Usuario usuario) {

        if (vazio(usuario.getNome()) || vazio(usuario.getEmail()) || vazio(usuario.getSenha())) {
            throw new RegraNegocioException("Nome, e-mail e senha são obrigatórios");
        }

        if (usuario.getSenha().length() < TAMANHO_MINIMO_SENHA) {
            throw new RegraNegocioException("A senha deve ter no mínimo " + TAMANHO_MINIMO_SENHA + " caracteres");
        }

        if (repository.existsByEmail(usuario.getEmail())) {
            throw new EmailJaCadastradoException("Já existe um usuário com este e-mail");
        }
        return repository.save(usuario);
    }


    public LoginResposta login(String email, String senha) {
        Usuario usuario = repository.findByEmail(email)
                .orElseThrow(() -> new CredenciaisInvalidasException("E-mail ou senha inválidos"));
        if (!usuario.getSenha().equals(senha)) {
            throw new CredenciaisInvalidasException("E-mail ou senha inválidos");
        }
        return new LoginResposta("Login realizado com sucesso", UsuarioResponse.de(usuario));
    }


    public Usuario atualizar(Long id, Usuario dados) {
        Usuario existente = buscarPorId(id);

        if (vazio(dados.getNome()) || vazio(dados.getEmail())) {
            throw new RegraNegocioException("Nome e e-mail são obrigatórios");
        }


        if (!existente.getEmail().equals(dados.getEmail())
                && repository.existsByEmail(dados.getEmail())) {
            throw new EmailJaCadastradoException("Já existe um usuário com este e-mail");
        }

        existente.setNome(dados.getNome());
        existente.setEmail(dados.getEmail());


        if (!vazio(dados.getSenha())) {
            if (dados.getSenha().length() < TAMANHO_MINIMO_SENHA) {
                throw new RegraNegocioException("A senha deve ter no mínimo " + TAMANHO_MINIMO_SENHA + " caracteres");
            }
            existente.setSenha(dados.getSenha());
        }
        return repository.save(existente);
    }


    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Usuário não encontrado: " + id);
        }
        repository.deleteById(id);
    }

    private boolean vazio(String valor) {
        return valor == null || valor.isBlank();
    }
}

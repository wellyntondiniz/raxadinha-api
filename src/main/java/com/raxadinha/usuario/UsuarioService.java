package com.raxadinha.usuario;

import com.raxadinha.usuario.exception.AcessoNegadoException;
import com.raxadinha.usuario.exception.CredenciaisInvalidasException;
import com.raxadinha.usuario.exception.EmailJaCadastradoException;
import com.raxadinha.usuario.exception.NomeJaCadastradoException;
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
        return repository.findByAtivoTrue();
    }

    public Usuario buscarPorId(Long id) {
        return repository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado: " + id));
    }


    public Usuario salvar(Usuario usuario) {

        // Normaliza entrada (remove espaços nas pontas)
        if (usuario.getNome() != null) usuario.setNome(usuario.getNome().trim());
        if (usuario.getEmail() != null) usuario.setEmail(usuario.getEmail().trim());

        if (vazio(usuario.getNome()) || vazio(usuario.getEmail()) || vazio(usuario.getSenha())) {
            throw new RegraNegocioException("Nome, e-mail e senha são obrigatórios");
        }

        if (usuario.getSenha().length() < TAMANHO_MINIMO_SENHA) {
            throw new RegraNegocioException("A senha deve ter no mínimo " + TAMANHO_MINIMO_SENHA + " caracteres");
        }

        if (repository.existsByEmail(usuario.getEmail())) {
            throw new EmailJaCadastradoException("Já existe um usuário com este e-mail");
        }

        // RN: nome de usuário deve ser único (não permitir duplicidade)
        if (repository.existsByNomeIgnoreCase(usuario.getNome())) {
            throw new NomeJaCadastradoException("Este nome de usuário já está em uso");
        }
        return repository.save(usuario);
    }


    public LoginResposta login(String email, String senha) {
        Usuario usuario = repository.findByEmailAndAtivoTrue(email)
                .orElseThrow(() -> new CredenciaisInvalidasException("E-mail ou senha inválidos"));
        if (!usuario.getSenha().equals(senha)) {
            throw new CredenciaisInvalidasException("E-mail ou senha inválidos");
        }
        return new LoginResposta("Login realizado com sucesso", UsuarioResponse.de(usuario));
    }


    public Usuario atualizar(Long id, Usuario dados, Long solicitanteId) {
        // RN03 - o usuário só pode editar os seus próprios dados
        if (solicitanteId == null || !solicitanteId.equals(id)) {
            throw new AcessoNegadoException("Você só pode editar a sua própria conta");
        }
        Usuario existente = buscarPorId(id);

        // Normaliza entrada (remove espaços nas pontas)
        if (dados.getNome() != null) dados.setNome(dados.getNome().trim());
        if (dados.getEmail() != null) dados.setEmail(dados.getEmail().trim());

        if (vazio(dados.getNome()) || vazio(dados.getEmail())) {
            throw new RegraNegocioException("Nome e e-mail são obrigatórios");
        }


        if (!existente.getEmail().equals(dados.getEmail())
                && repository.existsByEmail(dados.getEmail())) {
            throw new EmailJaCadastradoException("Já existe um usuário com este e-mail");
        }

        // RN: nome de usuário deve permanecer único ao editar
        if (!existente.getNome().equalsIgnoreCase(dados.getNome())
                && repository.existsByNomeIgnoreCase(dados.getNome())) {
            throw new NomeJaCadastradoException("Este nome de usuário já está em uso");
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


    // Verifica em tempo real se um nome de usuário está disponível
    public boolean nomeDisponivel(String nome) {
        if (vazio(nome)) {
            return false;
        }
        return !repository.existsByNomeIgnoreCase(nome.trim());
    }


    public void excluir(Long id, Long solicitanteId) {
        // RN04 - o usuário só pode excluir a sua própria conta
        if (solicitanteId == null || !solicitanteId.equals(id)) {
            throw new AcessoNegadoException("Você só pode excluir a sua própria conta");
        }
        // Exclusão lógica (soft delete): mantém o registro, apenas marca como inativo
        Usuario usuario = repository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado: " + id));
        usuario.setAtivo(false);
        repository.save(usuario);
    }

    private boolean vazio(String valor) {
        return valor == null || valor.isBlank();
    }
}

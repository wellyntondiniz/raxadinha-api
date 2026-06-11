package com.raxadinha.usuario.dto;

import com.raxadinha.usuario.Usuario;

public record UsuarioResponse(Long id, String nome, String email) {

    public static UsuarioResponse de(Usuario usuario) {
        return new UsuarioResponse(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}

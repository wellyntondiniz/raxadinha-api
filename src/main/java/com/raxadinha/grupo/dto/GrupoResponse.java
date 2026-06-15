package com.raxadinha.grupo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.raxadinha.grupo.Grupo;
import com.raxadinha.usuario.dto.UsuarioResponse;
import java.util.List;

public record GrupoResponse(
    Long id,
    String name,
    String description,
    List<UsuarioResponse> users
) {
    @JsonProperty("nome")
    public String getNome() {
        return name;
    }

    @JsonProperty("descricao")
    public String getDescricao() {
        return description;
    }

    @JsonProperty("usuarios")
    public List<UsuarioResponse> getUsuarios() {
        return users;
    }

    public static GrupoResponse de(Grupo grupo) {
        List<UsuarioResponse> usersList = grupo.getUsuarios() == null ? List.of() :
            grupo.getUsuarios().stream().map(UsuarioResponse::de).toList();
        return new GrupoResponse(
            grupo.getId(),
            grupo.getNome(),
            grupo.getDescricao(),
            usersList
        );
    }
}

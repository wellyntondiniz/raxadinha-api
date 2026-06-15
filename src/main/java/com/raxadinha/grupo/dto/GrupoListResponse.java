package com.raxadinha.grupo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.raxadinha.grupo.Grupo;

public record GrupoListResponse(
    Long id,
    String name,
    String description,
    int usersCount
) {
    @JsonProperty("nome")
    public String getNome() {
        return name;
    }

    @JsonProperty("descricao")
    public String getDescricao() {
        return description;
    }

    public static GrupoListResponse de(Grupo grupo) {
        int count = grupo.getUsuarios() == null ? 0 : grupo.getUsuarios().size();
        return new GrupoListResponse(
            grupo.getId(),
            grupo.getNome(),
            grupo.getDescricao(),
            count
        );
    }
}

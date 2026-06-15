package com.raxadinha.grupo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record GrupoRequest(
    @JsonAlias("nome")
    @NotBlank(message = "O nome do grupo é obrigatório")
    String name,
    
    @JsonAlias("descricao")
    String description,
    
    @JsonAlias({"userIds", "usuariosIds"})
    List<Long> userIds
) {}

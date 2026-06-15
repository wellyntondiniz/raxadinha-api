package com.raxadinha.grupo.dto;

import jakarta.validation.constraints.NotNull;

public record AdicionarUsuarioRequest(
    @NotNull(message = "O ID do usuário é obrigatório")
    Long userId
) {}

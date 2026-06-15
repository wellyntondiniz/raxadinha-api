package com.raxadinha.grupo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MensagemRequest(
    @NotNull(message = "O ID do usuário é obrigatório")
    Long userId,

    @NotBlank(message = "O conteúdo da mensagem é obrigatório")
    String content
) {}

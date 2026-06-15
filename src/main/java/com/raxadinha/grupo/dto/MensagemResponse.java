package com.raxadinha.grupo.dto;

import com.raxadinha.grupo.Mensagem;
import java.time.LocalDateTime;

public record MensagemResponse(
    Long id,
    Long userId,
    String userName,
    String content,
    LocalDateTime sentAt
) {
    public static MensagemResponse de(Mensagem msg) {
        return new MensagemResponse(
            msg.getId(),
            msg.getUsuario().getId(),
            msg.getUsuario().getNome(),
            msg.getConteudo(),
            msg.getDataEnvio()
        );
    }
}

package com.raxadinha.usuario.exception;

// Lançada quando o usuário tenta editar/excluir uma conta que não é a dele (RN03/RN04)
public class AcessoNegadoException extends RuntimeException {
    public AcessoNegadoException(String mensagem) {
        super(mensagem);
    }
}

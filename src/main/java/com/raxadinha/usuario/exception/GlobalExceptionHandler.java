package com.raxadinha.usuario.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Map<String, Object>> montar(HttpStatus status, String mensagem) {
        Map<String, Object> corpo = new LinkedHashMap<>();
        corpo.put("timestamp", LocalDateTime.now().toString());
        corpo.put("status", status.value());
        corpo.put("erro", status.getReasonPhrase());
        corpo.put("message", mensagem);
        return ResponseEntity.status(status).body(corpo);
    }

    // E-mail já cadastrado (UC01 A1 / UC03 A1)
    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<Map<String, Object>> emailJaCadastrado(EmailJaCadastradoException e) {
        return montar(HttpStatus.CONFLICT, e.getMessage());
    }

    // Nome de usuário já em uso -> 409 Conflict
    @ExceptionHandler(NomeJaCadastradoException.class)
    public ResponseEntity<Map<String, Object>> nomeJaCadastrado(NomeJaCadastradoException e) {
        return montar(HttpStatus.CONFLICT, e.getMessage());
    }

    // Credenciais inválidas (UC02 A1)
    @ExceptionHandler(CredenciaisInvalidasException.class)
    public ResponseEntity<Map<String, Object>> credenciaisInvalidas(CredenciaisInvalidasException e) {
        return montar(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    // Recurso não encontrado (UC03/UC04 E1)
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> naoEncontrado(RecursoNaoEncontradoException e) {
        return montar(HttpStatus.NOT_FOUND, e.getMessage());
    }

    // Demais regras de negócio (campos obrigatórios, senha curta etc.)
    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<Map<String, Object>> regraNegocio(RegraNegocioException e) {
        return montar(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    // Validações dos DTOs com @Valid (RF05 / RN05)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> validacao(MethodArgumentNotValidException e) {
        FieldError primeiroErro = e.getBindingResult().getFieldErrors().stream().findFirst().orElse(null);
        String mensagem = primeiroErro != null ? primeiroErro.getDefaultMessage() : "Dados inválidos";
        return montar(HttpStatus.BAD_REQUEST, mensagem);
    }
}

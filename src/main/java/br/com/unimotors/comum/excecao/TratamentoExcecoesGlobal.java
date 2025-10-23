package br.com.unimotors.comum.excecao;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.Map;

@RestControllerAdvice
public class TratamentoExcecoesGlobal {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleValidacao(ConstraintViolationException ex, HttpServletRequest request) {
        var body = Map.of(
                "timestamp", OffsetDateTime.now().toString(),
                "status", 400,
                "erro", "Requisição inválida",
                "mensagem", ex.getMessage(),
                "caminho", request.getRequestURI()
        );
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleNaoEncontrado(EntityNotFoundException ex, HttpServletRequest request) {
        var body = Map.of(
                "timestamp", OffsetDateTime.now().toString(),
                "status", 404,
                "erro", "Recurso não encontrado",
                "mensagem", ex.getMessage(),
                "caminho", request.getRequestURI()
        );
        return ResponseEntity.status(404).body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArg(IllegalArgumentException ex, HttpServletRequest request) {
        var body = Map.of(
                "timestamp", OffsetDateTime.now().toString(),
                "status", 400,
                "erro", "Bad Request",
                "mensagem", ex.getMessage(),
                "caminho", request.getRequestURI()
        );
        return ResponseEntity.badRequest().body(body);
    }
}

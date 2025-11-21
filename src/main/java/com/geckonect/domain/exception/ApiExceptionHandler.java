package com.geckonect.api.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.geckonect.domain.exception.CompetenciaNaoEncontradaException;
import com.geckonect.domain.exception.MatriculaNaoEncontradaException;
import com.geckonect.domain.exception.QuestionarioNaoEncontradoException; // NOVO IMPORT
import com.geckonect.domain.exception.TrilhaNaoEncontradaException;
import com.geckonect.domain.exception.UsuarioNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

    private record ErrorDetails(LocalDateTime timestamp, HttpStatus status, String message, List<String> details) {}


    // Tratamento para Recursos Não Encontrados (Status 404)
    @ExceptionHandler({UsuarioNaoEncontradoException.class, TrilhaNaoEncontradaException.class,
            CompetenciaNaoEncontradaException.class, MatriculaNaoEncontradaException.class,
            QuestionarioNaoEncontradoException.class}) // QUESTIONARIO ADICIONADO
    public ResponseEntity<ErrorDetails> handleNaoEncontrado(RuntimeException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorDetails details = new ErrorDetails(
                LocalDateTime.now(),
                status,
                "Recurso não encontrado.",
                List.of(ex.getMessage())
        );
        return new ResponseEntity<>(details, status);
    }

    // Tratamento para Bean Validation (Status 400 Bad Request)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidationExceptions(MethodArgumentNotValidException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<String> errors = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    return String.format("%s: %s", fieldName, errorMessage);
                })
                .collect(Collectors.toList());

        ErrorDetails details = new ErrorDetails(
                LocalDateTime.now(),
                status,
                "Erro de validação de dados na requisição.",
                errors
        );
        return new ResponseEntity<>(details, status);
    }

    // Tratamento para Erro de ENUM ou Formato (Status 400 Bad Request)
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorDetails> handleInvalidFormat(InvalidFormatException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String fieldName = ex.getPath().get(0).getFieldName();
        String targetType = ex.getTargetType().getSimpleName();
        String errorMessage;

        if (ex.getTargetType().isEnum()) {
            // Lista os valores aceitos para o Enum
            String enumValues = Arrays.stream(ex.getTargetType().getEnumConstants())
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
            errorMessage = String.format("O valor '%s' para o campo '%s' é inválido. Valores aceitos: [%s]",
                    ex.getValue(), fieldName, enumValues);
        } else {
            errorMessage = String.format("Valor inválido para o campo '%s'. Esperado tipo: %s",
                    fieldName, targetType);
        }

        ErrorDetails details = new ErrorDetails(
                LocalDateTime.now(),
                status,
                "Erro de formato no campo da requisição.",
                List.of(errorMessage)
        );
        return new ResponseEntity<>(details, status);
    }

    // Tratamento Genérico (Status 500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGenericException(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorDetails details = new ErrorDetails(
                LocalDateTime.now(),
                status,
                "Erro interno do servidor.",
                List.of(ex.getMessage())
        );

        return new ResponseEntity<>(details, status);
    }
}
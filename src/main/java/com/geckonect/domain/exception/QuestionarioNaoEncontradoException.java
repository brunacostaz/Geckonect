package com.geckonect.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção quando um questionário não é encontrado (HTTP 404)
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class QuestionarioNaoEncontradoException extends RuntimeException {

    public QuestionarioNaoEncontradoException(String message) {
        super(message);
    }
}
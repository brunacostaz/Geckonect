package com.geckonect.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção quando uma matrícula não é encontrada (HTTP 404)
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MatriculaNaoEncontradaException extends RuntimeException {

    public MatriculaNaoEncontradaException(String message) {
        super(message);
    }
}
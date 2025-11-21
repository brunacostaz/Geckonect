package com.geckonect.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção quando uma competência não é encontrada (HTTP 404)
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CompetenciaNaoEncontradaException extends RuntimeException {

    public CompetenciaNaoEncontradaException(String message) {
        super(message);
    }
}
package com.geckonect.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção quando uma trilha não é encontrada (HTTP 404)
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class TrilhaNaoEncontradaException extends RuntimeException {

    public TrilhaNaoEncontradaException(String message) {
        super(message);
    }
}
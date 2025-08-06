package com.metaphorce.TaskFlow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class RecursoExistenteException extends RuntimeException {

    public RecursoExistenteException(String mensaje) {
        super(mensaje);
    }
}
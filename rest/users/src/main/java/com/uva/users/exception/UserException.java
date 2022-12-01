package com.uva.users.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class UserException extends RuntimeException{
    public UserException(String mensaje) {
        super(mensaje);
    }
}

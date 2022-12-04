package com.uva.autenticacionUsers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class AuthException extends RuntimeException {
    public AuthException(String mensaje){
        super(mensaje);
    }
}

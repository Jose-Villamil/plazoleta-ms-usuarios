package com.usuariosplazoleta.microservicio_usuarios.infrastructure.exceptionhandler;

import com.usuariosplazoleta.microservicio_usuarios.domain.exception.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = "error";

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Map<String, String>> handleNoDataFoundException(DomainException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ex.getMessage()));
    }

}

package com.usuariosplazoleta.microservicio_usuarios.infrastructure.exceptionhandler;

import com.usuariosplazoleta.microservicio_usuarios.domain.exception.DomainException;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

import static com.usuariosplazoleta.microservicio_usuarios.infrastructure.configuration.Constants.ERROR_MICROSERVICE_COMMUNICATION;

@ControllerAdvice
public class ControllerAdvisor {
    private static final String MESSAGE = "error";

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Map<String, String>> handleNoDataFoundException(DomainException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ex.getMessage()));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Map<String, String>> handleFeignException(FeignException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap(MESSAGE, ERROR_MICROSERVICE_COMMUNICATION));
    }

}

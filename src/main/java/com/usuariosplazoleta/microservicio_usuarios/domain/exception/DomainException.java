package com.usuariosplazoleta.microservicio_usuarios.domain.exception;

public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}

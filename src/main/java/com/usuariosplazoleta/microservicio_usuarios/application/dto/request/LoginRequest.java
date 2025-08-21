package com.usuariosplazoleta.microservicio_usuarios.application.dto.request;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String email;
    private String password;
}

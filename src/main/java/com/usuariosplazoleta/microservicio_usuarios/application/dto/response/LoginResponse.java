package com.usuariosplazoleta.microservicio_usuarios.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    int expires;
    String token;
}

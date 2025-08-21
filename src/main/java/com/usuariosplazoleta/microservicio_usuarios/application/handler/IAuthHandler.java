package com.usuariosplazoleta.microservicio_usuarios.application.handler;

import com.usuariosplazoleta.microservicio_usuarios.application.dto.request.LoginRequest;
import com.usuariosplazoleta.microservicio_usuarios.application.dto.response.LoginResponse;

public interface IAuthHandler {
    LoginResponse login(LoginRequest loginRequest);
}

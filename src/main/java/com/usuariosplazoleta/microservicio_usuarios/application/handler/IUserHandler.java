package com.usuariosplazoleta.microservicio_usuarios.application.handler;

import com.usuariosplazoleta.microservicio_usuarios.application.dto.request.UserRequestDto;

public interface IUserHandler {
    void saveUser(UserRequestDto userRequestDto);
}

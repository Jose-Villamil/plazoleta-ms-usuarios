package com.usuariosplazoleta.microservicio_usuarios.application.handler;

import com.usuariosplazoleta.microservicio_usuarios.application.dto.request.UserRequestDto;
import com.usuariosplazoleta.microservicio_usuarios.application.dto.response.UserResponseDto;

public interface IUserHandler {
    void saveUser(UserRequestDto userRequestDto);
    UserResponseDto getUserById(Long id);
}

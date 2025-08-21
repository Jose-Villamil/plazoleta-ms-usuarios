package com.usuariosplazoleta.microservicio_usuarios.application.handler;

import com.usuariosplazoleta.microservicio_usuarios.application.dto.request.UserEmployeeRequestDto;
import com.usuariosplazoleta.microservicio_usuarios.application.dto.request.UserRequestDto;
import com.usuariosplazoleta.microservicio_usuarios.application.dto.response.UserResponseDto;

public interface IUserHandler {
    void saveUser(UserRequestDto userRequestDto);
    void saveUserEmployee(UserEmployeeRequestDto userEmployeeRequestDto);
    UserResponseDto getUserById(Long id);
}

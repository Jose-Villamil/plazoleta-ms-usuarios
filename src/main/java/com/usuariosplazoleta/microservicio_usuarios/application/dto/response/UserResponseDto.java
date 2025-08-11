package com.usuariosplazoleta.microservicio_usuarios.application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private String name;
    private String lastName;
    private String document;
    private String phoneNumber;
    private String birthDate;
    private String email;
    private String password;
    private RoleResponseDto role;
}

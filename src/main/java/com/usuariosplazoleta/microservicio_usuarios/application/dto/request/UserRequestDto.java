package com.usuariosplazoleta.microservicio_usuarios.application.dto.request;

import com.usuariosplazoleta.microservicio_usuarios.domain.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRequestDto {
    private String name;
    private String lastName;
    private String document;
    private String phoneNumber;
    private String birthDate;
    private String email;
    private String password;
    //private Role role;
}

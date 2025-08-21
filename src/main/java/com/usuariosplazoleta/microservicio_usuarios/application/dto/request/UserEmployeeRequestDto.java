package com.usuariosplazoleta.microservicio_usuarios.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserEmployeeRequestDto {
    private String name;
    private String lastName;
    private String document;
    private String phoneNumber;
    private String email;
    private String password;
    private Long restaurantId;
}

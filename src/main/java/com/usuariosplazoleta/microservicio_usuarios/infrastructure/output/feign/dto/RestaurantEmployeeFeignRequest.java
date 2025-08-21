package com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RestaurantEmployeeFeignRequest {
    private Long restaurantId;
    private Long employeeId;
}

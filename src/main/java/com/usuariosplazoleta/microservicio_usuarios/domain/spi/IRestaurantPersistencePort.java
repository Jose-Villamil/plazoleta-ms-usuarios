package com.usuariosplazoleta.microservicio_usuarios.domain.spi;

import com.usuariosplazoleta.microservicio_usuarios.domain.model.RestaurantEmployee;

public interface IRestaurantPersistencePort {
    void saveRestaurantEmployee(RestaurantEmployee restaurantEmployee);
}

package com.usuariosplazoleta.microservicio_usuarios.domain.api;

import com.usuariosplazoleta.microservicio_usuarios.domain.model.User;

import java.util.Optional;

public interface IUserServicePort {
    User saveUser(User user, String roleAuth);
    User saveUserEmployee(User user, Long idRestaurant,String roleAuth);
    User saveUserClient(User user);
    Optional<User> getUserById(Long id);
}
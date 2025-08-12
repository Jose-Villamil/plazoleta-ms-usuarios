package com.usuariosplazoleta.microservicio_usuarios.domain.api;

import com.usuariosplazoleta.microservicio_usuarios.domain.model.User;

import java.util.Optional;

public interface IUserServicePort {
    void saveUser(User user, String roleAuth);
    Optional<User> getUserById(Long id);
}

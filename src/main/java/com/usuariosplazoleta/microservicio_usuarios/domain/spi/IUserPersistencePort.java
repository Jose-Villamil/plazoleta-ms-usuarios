package com.usuariosplazoleta.microservicio_usuarios.domain.spi;

import com.usuariosplazoleta.microservicio_usuarios.domain.model.User;

import java.util.Optional;

public interface IUserPersistencePort {
    void saveUser(User user);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
}

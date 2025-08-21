package com.usuariosplazoleta.microservicio_usuarios.domain.spi;

import com.usuariosplazoleta.microservicio_usuarios.domain.model.Role;

import java.util.Optional;

public interface IRolePersistencePort {
    Optional<Role> findByName(String name);
}
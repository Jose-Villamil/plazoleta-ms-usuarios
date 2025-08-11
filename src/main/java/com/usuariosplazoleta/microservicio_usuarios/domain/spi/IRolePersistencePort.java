package com.usuariosplazoleta.microservicio_usuarios.domain.spi;

import com.usuariosplazoleta.microservicio_usuarios.domain.model.Role;

import java.util.List;

public interface IRolePersistencePort {
    Role findByName(String name);
    List<Role> getAllRoles();
}
package com.usuariosplazoleta.microservicio_usuarios.domain.api;

import com.usuariosplazoleta.microservicio_usuarios.domain.model.Role;

import java.util.Optional;

public interface IRoleServicePort {
   Optional<Role> findByName(String name);
}

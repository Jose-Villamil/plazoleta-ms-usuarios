package com.usuariosplazoleta.microservicio_usuarios.domain.api;

import com.usuariosplazoleta.microservicio_usuarios.domain.model.Role;

public interface IRoleServicePort {
    Role findByName(String name);
}

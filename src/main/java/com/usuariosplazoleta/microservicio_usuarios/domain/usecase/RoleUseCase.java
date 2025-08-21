package com.usuariosplazoleta.microservicio_usuarios.domain.usecase;

import com.usuariosplazoleta.microservicio_usuarios.domain.api.IRoleServicePort;
import com.usuariosplazoleta.microservicio_usuarios.domain.model.Role;
import com.usuariosplazoleta.microservicio_usuarios.domain.spi.IRolePersistencePort;

import java.util.Optional;

public class RoleUseCase implements IRoleServicePort {

    private final IRolePersistencePort rolePersistencePort;
    public RoleUseCase(IRolePersistencePort rolePersistencePort) {
        this.rolePersistencePort = rolePersistencePort;
    }

    @Override
    public Optional<Role> findByName(String name) {
        return rolePersistencePort.findByName(name);
    }

}

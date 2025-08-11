package com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.jpa.adapter;

import com.usuariosplazoleta.microservicio_usuarios.domain.model.Role;
import com.usuariosplazoleta.microservicio_usuarios.domain.spi.IRolePersistencePort;
import com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.jpa.entity.RoleEntity;
import com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.jpa.mapper.IRoleEntityMapper;
import com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.jpa.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RoleJpaAdapter implements IRolePersistencePort {

    private final IRoleRepository repository;
    private final IRoleEntityMapper iRoleEntityMapper;

    @Override
    public Role findByName(String name) {
        RoleEntity entity = repository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + name));
        return iRoleEntityMapper.toRole(entity);
    }

    @Override
    public List<Role> getAllRoles() {
        return List.of();
    }
}

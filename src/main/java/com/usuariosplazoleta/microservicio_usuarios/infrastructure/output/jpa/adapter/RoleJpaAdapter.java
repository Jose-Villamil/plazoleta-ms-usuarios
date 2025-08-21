package com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.jpa.adapter;

import com.usuariosplazoleta.microservicio_usuarios.domain.model.Role;
import com.usuariosplazoleta.microservicio_usuarios.domain.spi.IRolePersistencePort;
import com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.jpa.mapper.IRoleEntityMapper;
import com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.jpa.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class RoleJpaAdapter implements IRolePersistencePort {

    private final IRoleRepository repository;
    private final IRoleEntityMapper iRoleEntityMapper;

    @Override
    public Optional<Role> findByName(String name) {
       return repository.findByName(name).map(iRoleEntityMapper::toRole);
    }
}

package com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.jpa.repository;

import com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {
}

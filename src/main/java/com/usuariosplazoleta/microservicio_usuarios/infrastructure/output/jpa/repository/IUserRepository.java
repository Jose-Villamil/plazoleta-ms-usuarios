package com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.jpa.repository;

import com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
}

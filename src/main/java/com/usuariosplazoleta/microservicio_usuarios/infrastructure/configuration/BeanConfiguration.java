package com.usuariosplazoleta.microservicio_usuarios.infrastructure.configuration;

import com.usuariosplazoleta.microservicio_usuarios.domain.api.IRoleServicePort;
import com.usuariosplazoleta.microservicio_usuarios.domain.api.IUserServicePort;
import com.usuariosplazoleta.microservicio_usuarios.domain.spi.IRolePersistencePort;
import com.usuariosplazoleta.microservicio_usuarios.domain.spi.IUserPersistencePort;
import com.usuariosplazoleta.microservicio_usuarios.domain.usecase.RoleUseCase;
import com.usuariosplazoleta.microservicio_usuarios.domain.usecase.UserUseCase;
import com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.jpa.adapter.RoleJpaAdapter;
import com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.jpa.adapter.UserJpaAdapter;
import com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.jpa.mapper.IRoleEntityMapper;
import com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.jpa.mapper.IUserEntityMapper;
import com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.jpa.repository.IRoleRepository;
import com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    private final PasswordEncoder passwordEncoder;
    private final IRoleRepository roleRepository;
    private final IRoleEntityMapper roleEntityMapper;

    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(userPersistencePort(), rolePersistencePort());
    }

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserJpaAdapter(userRepository, userEntityMapper, passwordEncoder);
    }

    @Bean
    public IRoleServicePort roleServicePort() {
        return new RoleUseCase(rolePersistencePort());
    }

    @Bean
    public IRolePersistencePort rolePersistencePort() {
        return new RoleJpaAdapter(roleRepository, roleEntityMapper);
    }

}

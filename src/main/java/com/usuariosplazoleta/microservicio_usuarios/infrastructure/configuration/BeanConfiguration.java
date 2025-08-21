package com.usuariosplazoleta.microservicio_usuarios.infrastructure.configuration;

import com.usuariosplazoleta.microservicio_usuarios.domain.api.IRoleServicePort;
import com.usuariosplazoleta.microservicio_usuarios.domain.api.IUserServicePort;
import com.usuariosplazoleta.microservicio_usuarios.domain.spi.IRestaurantPersistencePort;
import com.usuariosplazoleta.microservicio_usuarios.domain.spi.IRolePersistencePort;
import com.usuariosplazoleta.microservicio_usuarios.domain.spi.IUserPersistencePort;
import com.usuariosplazoleta.microservicio_usuarios.domain.usecase.RoleUseCase;
import com.usuariosplazoleta.microservicio_usuarios.domain.usecase.UserUseCase;
import com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.feign.adapter.RestaurantFeignAdapter;
import com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.feign.client.IRestaurantFeignClient;
import com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.feign.mapper.IRestaurantFeignMapper;
import com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.jpa.adapter.RoleJpaAdapter;
import com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.jpa.adapter.UserJpaAdapter;
import com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.jpa.mapper.IRoleEntityMapper;
import com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.jpa.mapper.IUserEntityMapper;
import com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.jpa.repository.IRoleRepository;
import com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    private final IRoleRepository roleRepository;
    private final IRoleEntityMapper roleEntityMapper;
    private final IRestaurantFeignClient  restaurantFeignClient;
    private final IRestaurantFeignMapper  restaurantFeignMapper;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(userPersistencePort(), rolePersistencePort(), restaurantPersistentPort());
    }

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserJpaAdapter(userRepository, userEntityMapper, passwordEncoder());
    }

    @Bean
    public IRoleServicePort roleServicePort() {
        return new RoleUseCase(rolePersistencePort());
    }

    @Bean
    public IRolePersistencePort rolePersistencePort() {
        return new RoleJpaAdapter(roleRepository, roleEntityMapper);
    }

    @Bean
    public IRestaurantPersistencePort restaurantPersistentPort() {
        return new RestaurantFeignAdapter(restaurantFeignClient, restaurantFeignMapper);
    }

}

package com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.jpa.adapter;

import com.usuariosplazoleta.microservicio_usuarios.domain.model.User;
import com.usuariosplazoleta.microservicio_usuarios.domain.spi.IUserPersistencePort;
import com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.jpa.entity.UserEntity;
import com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.jpa.mapper.IUserEntityMapper;
import com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity entity = userRepository.save(userEntityMapper.toEntity(user));
       return userEntityMapper.toUser(entity);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id)
                .map(userEntityMapper::toUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userEntityMapper::toUser);
    }
}

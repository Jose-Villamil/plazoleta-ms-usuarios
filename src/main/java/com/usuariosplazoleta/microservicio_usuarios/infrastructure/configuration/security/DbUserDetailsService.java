package com.usuariosplazoleta.microservicio_usuarios.infrastructure.configuration.security;

import com.usuariosplazoleta.microservicio_usuarios.domain.model.User;
import com.usuariosplazoleta.microservicio_usuarios.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DbUserDetailsService implements UserDetailsService {
    private final IUserPersistencePort userPort;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userPort.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(""));

        String roleName = user.getRole().getName();

        var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + roleName.toUpperCase()));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), authorities);
    }
}

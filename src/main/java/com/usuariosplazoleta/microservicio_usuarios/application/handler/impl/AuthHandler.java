package com.usuariosplazoleta.microservicio_usuarios.application.handler.impl;

import com.usuariosplazoleta.microservicio_usuarios.application.dto.request.LoginRequest;
import com.usuariosplazoleta.microservicio_usuarios.application.dto.response.LoginResponse;
import com.usuariosplazoleta.microservicio_usuarios.application.handler.IAuthHandler;
import com.usuariosplazoleta.microservicio_usuarios.domain.exception.DomainException;
import com.usuariosplazoleta.microservicio_usuarios.domain.spi.IUserPersistencePort;
import com.usuariosplazoleta.microservicio_usuarios.infrastructure.configuration.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthHandler implements IAuthHandler {
    private final AuthenticationManager authManager;
    private final JwtProvider jwt;
    private final IUserPersistencePort userPort;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        var user = userPort.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new DomainException("Usuario invalido"));
        var roles = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        var token = jwt.generate(user.getEmail(), user.getId(), roles);

        return new LoginResponse(120, token);
    }
}

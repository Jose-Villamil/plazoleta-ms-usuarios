package com.usuariosplazoleta.microservicio_usuarios.application.handler.impl;

import com.usuariosplazoleta.microservicio_usuarios.application.dto.request.UserRequestDto;
import com.usuariosplazoleta.microservicio_usuarios.application.handler.IUserHandler;
import com.usuariosplazoleta.microservicio_usuarios.application.mapper.IUserRequestMapper;
import com.usuariosplazoleta.microservicio_usuarios.application.mapper.IUserResponseMapper;
import com.usuariosplazoleta.microservicio_usuarios.domain.api.IUserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserHandler implements IUserHandler {
    private final IUserServicePort userServicePort;
    private final IUserRequestMapper userRequestMapper;
    private final IUserResponseMapper userResponseMapper;

    @Override
    public void saveUser(UserRequestDto userRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String roleAuth = authentication.getAuthorities().iterator().next().getAuthority();
        userServicePort.saveUser(userRequestMapper.toUser(userRequestDto), roleAuth);
    }
}

package com.usuariosplazoleta.microservicio_usuarios.domain.api;

import com.usuariosplazoleta.microservicio_usuarios.domain.model.User;

public interface IUserServicePort {
    void saveUser(User user, String roleAuth);
}

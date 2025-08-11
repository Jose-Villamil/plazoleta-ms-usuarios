package com.usuariosplazoleta.microservicio_usuarios.domain.spi;

import com.usuariosplazoleta.microservicio_usuarios.domain.model.User;

public interface IUserPersistencePort {
    void saveUser(User user);
}

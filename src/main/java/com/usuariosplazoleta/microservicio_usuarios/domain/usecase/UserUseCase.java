package com.usuariosplazoleta.microservicio_usuarios.domain.usecase;

import com.usuariosplazoleta.microservicio_usuarios.domain.api.IUserServicePort;
import com.usuariosplazoleta.microservicio_usuarios.domain.model.Role;
import com.usuariosplazoleta.microservicio_usuarios.domain.model.User;
import com.usuariosplazoleta.microservicio_usuarios.domain.spi.IRolePersistencePort;
import com.usuariosplazoleta.microservicio_usuarios.domain.spi.IUserPersistencePort;

import java.util.Optional;

import static com.usuariosplazoleta.microservicio_usuarios.domain.util.Constantes.ROLE_PROPIETARIO;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IRolePersistencePort rolePersistencePort;

    public UserUseCase(IUserPersistencePort userPersistencePort, IRolePersistencePort rolePersistencePort) {
        this.userPersistencePort = userPersistencePort;
        this.rolePersistencePort = rolePersistencePort;
    }

    @Override
    public void saveUser(User user, String roleAuth) {
        UserValidator.validate(user);
        user.setRole(assignRoleNewUser(roleAuth));
        userPersistencePort.saveUser(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userPersistencePort.findById(id);
    }

    private Role assignRoleNewUser(String roleAuth) {
        return rolePersistencePort.findByName(ROLE_PROPIETARIO);
    }
}

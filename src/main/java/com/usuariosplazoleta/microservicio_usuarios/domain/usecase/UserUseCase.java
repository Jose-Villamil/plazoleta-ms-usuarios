package com.usuariosplazoleta.microservicio_usuarios.domain.usecase;

import com.usuariosplazoleta.microservicio_usuarios.domain.api.IUserServicePort;
import com.usuariosplazoleta.microservicio_usuarios.domain.exception.DomainException;
import com.usuariosplazoleta.microservicio_usuarios.domain.model.RestaurantEmployee;
import com.usuariosplazoleta.microservicio_usuarios.domain.model.Role;
import com.usuariosplazoleta.microservicio_usuarios.domain.model.User;
import com.usuariosplazoleta.microservicio_usuarios.domain.spi.IRestaurantPersistencePort;
import com.usuariosplazoleta.microservicio_usuarios.domain.spi.IRolePersistencePort;
import com.usuariosplazoleta.microservicio_usuarios.domain.spi.IUserPersistencePort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.usuariosplazoleta.microservicio_usuarios.domain.util.Constantes.*;
import static com.usuariosplazoleta.microservicio_usuarios.domain.util.DomainMessages.*;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IRolePersistencePort rolePersistencePort;
    private final IRestaurantPersistencePort restaurantPersistentPort;

    public UserUseCase(IUserPersistencePort userPersistencePort, IRolePersistencePort rolePersistencePort , IRestaurantPersistencePort restaurantPersistentPort) {
        this.userPersistencePort = userPersistencePort;
        this.rolePersistencePort = rolePersistencePort;
        this.restaurantPersistentPort = restaurantPersistentPort;
    }

    @Override
    public User saveUser(User user, String roleAuth) {
        UserValidator.validateUserOwner(user);
        String role = normalizeRole(roleAuth);
        if (!ROLE_ADMIN.equalsIgnoreCase(role)) {
            throw new DomainException(String.format(USER_DOESNOT_HAVE_ROL, role));
        }
        Role ownerRole =  rolePersistencePort.findByName(ROLE_OWNER)
                .orElseThrow(() -> new DomainException(String.format(ROLE_NOT_FOUND, ROLE_OWNER)));

        user.setRole(ownerRole);
        return userPersistencePort.saveUser(user);
    }

    @Override
    @Transactional
    public User saveUserEmployee(User user, Long idRestaurant, String roleAuth) {
        UserValidator.validateEmployee(user);
        String role = normalizeRole(roleAuth);
        if (!ROLE_OWNER.equalsIgnoreCase(role)) {
            throw new DomainException(String.format(USER_DOESNOT_HAVE_ROL, role));
        }
        Role employeeRole =  rolePersistencePort.findByName(ROLE_EMPLOYEE).orElseThrow(() -> new DomainException(String.format(ROLE_NOT_FOUND, ROLE_EMPLOYEE)));
        user.setRole(employeeRole);
        User userSave= userPersistencePort.saveUser(user);

        try {
            restaurantPersistentPort.saveRestaurantEmployee(new RestaurantEmployee(idRestaurant, userSave.getId()));
        } catch (DomainException ex) {
            throw new DomainException(String.format(EMPLOYEE_CREATED_WHIT_ERROR, ex.getMessage()));
        }
        return userSave;
    }

    @Override
    public User saveUserClient(User user) {
        UserValidator.validateClient(user);
        Role clientRole = rolePersistencePort.findByName(ROLE_CLIENT)
                .orElseThrow(() -> new DomainException(String.format(ROLE_NOT_FOUND, ROLE_CLIENT)));
        user.setRole(clientRole);
        return userPersistencePort.saveUser(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userPersistencePort.findById(id);
    }

    private String normalizeRole(String roleAuth) {
        if (roleAuth == null) return "";
        return roleAuth.startsWith("ROLE_") ? roleAuth.substring(5) : roleAuth;
    }
}

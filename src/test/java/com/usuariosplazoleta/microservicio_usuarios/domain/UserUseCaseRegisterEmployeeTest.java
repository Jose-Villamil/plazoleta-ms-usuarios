package com.usuariosplazoleta.microservicio_usuarios.domain;

import com.usuariosplazoleta.microservicio_usuarios.domain.exception.DomainException;
import com.usuariosplazoleta.microservicio_usuarios.domain.model.RestaurantEmployee;
import com.usuariosplazoleta.microservicio_usuarios.domain.model.Role;
import com.usuariosplazoleta.microservicio_usuarios.domain.model.User;
import com.usuariosplazoleta.microservicio_usuarios.domain.spi.IRestaurantPersistencePort;
import com.usuariosplazoleta.microservicio_usuarios.domain.spi.IRolePersistencePort;
import com.usuariosplazoleta.microservicio_usuarios.domain.spi.IUserPersistencePort;
import com.usuariosplazoleta.microservicio_usuarios.domain.usecase.UserUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.usuariosplazoleta.microservicio_usuarios.domain.util.Constantes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseRegisterEmployeeTest {
    @Mock
    private IUserPersistencePort userPersistencePort;
    @Mock private IRolePersistencePort rolePersistencePort;
    @Mock private IRestaurantPersistencePort restaurantPersistencePort;
    @InjectMocks
    UserUseCase useCase;

    private static User validEmployeeToCreate() {
        User u = new User();
        u.setName("Carlos");
        u.setLastName("López");
        u.setDocument("900800700");
        u.setPhoneNumber("+573009998877");
        u.setEmail("carlos@example.com");
        u.setPassword("Clave123");
        return u;
    }

    @Test
    void ownerCreatesEmployee_ok_setsEmployeeRole_persists_andAssociates() {
        User input = validEmployeeToCreate();
        Role employeeRole = new Role(); employeeRole.setName(ROLE_EMPLOYEE);

        when(rolePersistencePort.findByName(ROLE_EMPLOYEE)).thenReturn(Optional.of(employeeRole));
        when(userPersistencePort.saveUser(any(User.class))).thenAnswer(inv -> {
            User arg = inv.getArgument(0);
            arg.setId(42L);
            return arg;
        });

        useCase.saveUserEmployee(input, 7L, "ROLE_PROPIETARIO");

        assertEquals(ROLE_EMPLOYEE, input.getRole().getName());
        ArgumentCaptor<RestaurantEmployee> cap = ArgumentCaptor.forClass(RestaurantEmployee.class);
        verify(restaurantPersistencePort).saveRestaurantEmployee(cap.capture());
        assertEquals(7L, cap.getValue().getRestaurantId());
        assertEquals(42L, cap.getValue().getEmployeeId());
    }

    @Test
    void whenCallerNotOwner_throwsDomainException() {
        User input = validEmployeeToCreate();
        DomainException ex = assertThrows(DomainException.class,
                () -> useCase.saveUserEmployee(input, 5L, "ROLE_ADMINISTRADOR"));
        assertTrue(ex.getMessage().toLowerCase().contains("permiso") || ex.getMessage().toLowerCase().contains("rol"));
        verifyNoInteractions(rolePersistencePort, userPersistencePort, restaurantPersistencePort);
    }

    @Test
    void whenRestaurantAssociationFails_throwsDomainException() {
        User input = validEmployeeToCreate();
        Role employeeRole = new Role(); employeeRole.setName(ROLE_EMPLOYEE);

        when(rolePersistencePort.findByName(ROLE_EMPLOYEE)).thenReturn(Optional.of(employeeRole));
        when(userPersistencePort.saveUser(any(User.class))).thenAnswer(inv -> {
            User arg = inv.getArgument(0);
            arg.setId(33L);
            return arg;
        });
        doThrow(new RuntimeException("feign 500"))
                .when(restaurantPersistencePort).saveRestaurantEmployee(any(RestaurantEmployee.class));

        DomainException ex = assertThrows(DomainException.class,
                () -> useCase.saveUserEmployee(input, 9L, "ROLE_PROPIETARIO"));

        assertTrue(ex.getMessage().toLowerCase().contains("vincul") || ex.getMessage().toLowerCase().contains("creado"));
        verify(rolePersistencePort).findByName(ROLE_EMPLOYEE);
        verify(userPersistencePort).saveUser(any(User.class));
        verify(restaurantPersistencePort).saveRestaurantEmployee(any(RestaurantEmployee.class));
    }

    @Test
    void whenEmployeeRoleNotFound_throwsDomainException() {
        User input = validEmployeeToCreate();
        when(rolePersistencePort.findByName(ROLE_EMPLOYEE)).thenReturn(Optional.empty());

        DomainException ex = assertThrows(DomainException.class,
                () -> useCase.saveUserEmployee(input, 5L, "ROLE_PROPIETARIO"));

        assertTrue(ex.getMessage().toLowerCase().contains("rol"));
        verify(rolePersistencePort).findByName(ROLE_EMPLOYEE);
        verifyNoInteractions(userPersistencePort, restaurantPersistencePort);
    }

}

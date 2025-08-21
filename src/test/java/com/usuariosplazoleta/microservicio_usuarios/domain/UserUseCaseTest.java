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

import java.time.LocalDate;
import java.util.Optional;

import static com.usuariosplazoleta.microservicio_usuarios.domain.util.Constantes.*;
        import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.ArgumentMatchers.*;
        import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock private IUserPersistencePort userPersistencePort;
    @Mock private IRolePersistencePort rolePersistencePort;
    @Mock private IRestaurantPersistencePort restaurantPersistencePort;
    @InjectMocks
    UserUseCase useCase;

    private static User validOwnerToCreate() {
        User u = new User();
        u.setName("Ana");
        u.setLastName("Pérez");
        u.setDocument("100200300");
        u.setPhoneNumber("+573001112233");
        u.setBirthDate(LocalDate.now().minusYears(20));
        u.setEmail("ana@example.com");
        u.setPassword("Secreta123");
        return u;
    }

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
    void adminCreatesOwner_ok_setsOwnerRole_persists() {
        User input = validOwnerToCreate();
        Role ownerRole = new Role(); ownerRole.setName(ROLE_OWNER);

        when(rolePersistencePort.findByName(ROLE_OWNER)).thenReturn(Optional.of(ownerRole));
        when(userPersistencePort.saveUser(any(User.class))).thenAnswer(inv -> {
            User arg = inv.getArgument(0);
            arg.setId(10L);
            return arg;
        });

        User saved = useCase.saveUser(input, "ROLE_ADMINISTRADOR");

        assertEquals(ROLE_OWNER, input.getRole().getName());
        assertEquals(10L, saved.getId());
        verify(rolePersistencePort).findByName(ROLE_OWNER);
        verify(userPersistencePort).saveUser(any(User.class));
        verifyNoInteractions(restaurantPersistencePort);
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
    void whenCallerNotAdmin_throwsDomainException() {
        User input = validOwnerToCreate();
        DomainException ex = assertThrows(DomainException.class,
                () -> useCase.saveUser(input, "ROLE_PROPIETARIO"));
        assertTrue(ex.getMessage().toLowerCase().contains("permiso") || ex.getMessage().toLowerCase().contains("rol"));
        verifyNoInteractions(rolePersistencePort, userPersistencePort, restaurantPersistencePort);
    }

    @Test
    void whenOwnerRoleMissing_throwsDomainException() {
        User input = validOwnerToCreate();
        when(rolePersistencePort.findByName(ROLE_OWNER)).thenReturn(Optional.empty());

        DomainException ex = assertThrows(DomainException.class,
                () -> useCase.saveUser(input, "ROLE_ADMINISTRADOR"));
        assertTrue(ex.getMessage().toLowerCase().contains("rol"));
        verify(rolePersistencePort).findByName(ROLE_OWNER);
        verifyNoInteractions(userPersistencePort, restaurantPersistencePort);
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
    void whenEmployeeRoleMissing_throwsDomainException() {
        User input = validEmployeeToCreate();
        when(rolePersistencePort.findByName(ROLE_EMPLOYEE)).thenReturn(Optional.empty());

        DomainException ex = assertThrows(DomainException.class,
                () -> useCase.saveUserEmployee(input, 5L, "ROLE_PROPIETARIO"));

        assertTrue(ex.getMessage().toLowerCase().contains("rol"));
        verify(rolePersistencePort).findByName(ROLE_EMPLOYEE);
        verifyNoInteractions(userPersistencePort, restaurantPersistencePort);
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
    void getUserById_whenExists_returnsOptionalWithUser() {
        User u = new User(); u.setId(77L);
        when(userPersistencePort.findById(77L)).thenReturn(Optional.of(u));

        Optional<User> found = useCase.getUserById(77L);

        assertTrue(found.isPresent());
        assertEquals(77L, found.get().getId());
        verify(userPersistencePort).findById(77L);
    }

    @Test
    void getUserById_whenNotExists_returnsEmptyOptional() {
        when(userPersistencePort.findById(88L)).thenReturn(Optional.empty());

        Optional<User> found = useCase.getUserById(88L);

        assertTrue(found.isEmpty());
        verify(userPersistencePort).findById(88L);
    }
}


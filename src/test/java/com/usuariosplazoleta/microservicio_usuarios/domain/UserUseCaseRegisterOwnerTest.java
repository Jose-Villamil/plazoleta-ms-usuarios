package com.usuariosplazoleta.microservicio_usuarios.domain;

import com.usuariosplazoleta.microservicio_usuarios.domain.exception.DomainException;
import com.usuariosplazoleta.microservicio_usuarios.domain.model.Role;
import com.usuariosplazoleta.microservicio_usuarios.domain.model.User;
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
class UserUseCaseRegisterOwnerTest {
    @Mock IUserPersistencePort userPersistencePort;
    @Mock IRolePersistencePort rolePersistencePort;
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
    }

    @Test
    void whenCallerNotAdmin_throwsDomainException() {
        User input = validOwnerToCreate();
        DomainException ex = assertThrows(DomainException.class,
                () -> useCase.saveUser(input, "ROLE_PROPIETARIO"));
        assertTrue(ex.getMessage().toLowerCase().contains("permiso") || ex.getMessage().toLowerCase().contains("rol"));
        verifyNoInteractions(rolePersistencePort, userPersistencePort);
    }

    @Test
    void whenOwnerRoleMissing_throwsDomainException() {
        User input = validOwnerToCreate();
        when(rolePersistencePort.findByName(ROLE_OWNER)).thenReturn(Optional.empty());

        DomainException ex = assertThrows(DomainException.class,
                () -> useCase.saveUser(input, "ROLE_ADMINISTRADOR"));
        assertTrue(ex.getMessage().toLowerCase().contains("rol"));
        verify(rolePersistencePort).findByName(ROLE_OWNER);
    }


}

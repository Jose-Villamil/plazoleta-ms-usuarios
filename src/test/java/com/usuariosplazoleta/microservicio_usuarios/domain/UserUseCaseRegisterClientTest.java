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
import java.util.Optional;
import static com.usuariosplazoleta.microservicio_usuarios.domain.util.Constantes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseRegisterClientTest {
    @Mock IUserPersistencePort userPort;
    @Mock IRolePersistencePort rolePort;
    @InjectMocks
    UserUseCase useCase;

    private static User validClient() {
        User u = new User();
        u.setName("Ana");
        u.setLastName("Pérez");
        u.setDocument("123456789");
        u.setPhoneNumber("+573001112233");
        u.setEmail("ana@example.com");
        u.setPassword("Secreta123");
        return u;
    }

    @Test
    void registerClient() {
        User input = validClient();
        Role client = new Role(); client.setName(ROLE_CLIENT);

        when(rolePort.findByName(ROLE_CLIENT)).thenReturn(Optional.of(client));
        when(userPort.saveUser(any(User.class))).thenAnswer(inv -> {
            User u = inv.getArgument(0); u.setId(77L); return u;
        });

        User saved = useCase.saveUserClient(input);

        assertEquals(ROLE_CLIENT, input.getRole().getName());
        assertEquals(77L, saved.getId());
        verify(rolePort).findByName(ROLE_CLIENT);
        verify(userPort).saveUser(any(User.class));
    }

    @Test
    void registerClient_whenRoleMissing_throws() {
        User input = validClient();
        when(rolePort.findByName(ROLE_CLIENT)).thenReturn(Optional.empty());

        assertThrows(DomainException.class, () -> useCase.saveUserClient(input));
        verify(rolePort).findByName(ROLE_CLIENT);
    }

    @Test
    void registerClient_whenInvalidData_throws() {
        User input = validClient();
        input.setEmail("bad");
        assertThrows(DomainException.class, () -> useCase.saveUserClient(input));
    }
}



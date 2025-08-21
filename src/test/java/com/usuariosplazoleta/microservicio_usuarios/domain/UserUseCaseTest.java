package com.usuariosplazoleta.microservicio_usuarios.domain;

import com.usuariosplazoleta.microservicio_usuarios.domain.model.User;
import com.usuariosplazoleta.microservicio_usuarios.domain.spi.IUserPersistencePort;
import com.usuariosplazoleta.microservicio_usuarios.domain.usecase.UserUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock private IUserPersistencePort userPersistencePort;
    @InjectMocks
    UserUseCase useCase;

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


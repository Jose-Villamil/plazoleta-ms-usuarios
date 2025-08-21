package com.usuariosplazoleta.microservicio_usuarios.domain;

import com.usuariosplazoleta.microservicio_usuarios.domain.model.Role;
import com.usuariosplazoleta.microservicio_usuarios.domain.model.User;
import com.usuariosplazoleta.microservicio_usuarios.domain.spi.IRolePersistencePort;
import com.usuariosplazoleta.microservicio_usuarios.domain.spi.IUserPersistencePort;
import com.usuariosplazoleta.microservicio_usuarios.domain.usecase.RoleUseCase;
import com.usuariosplazoleta.microservicio_usuarios.domain.usecase.UserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RoleUseCaseTest {

    @Mock
    private IRolePersistencePort rolePersistencePort;
    @Mock
    private IUserPersistencePort userPersistencePort;

    @InjectMocks
    private RoleUseCase roleUseCase;

    @InjectMocks
    UserUseCase userUseCase;

    private User user;

    private Role role;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("Jose");
        user.setLastName("Villamil");
        user.setDocument("123456789");
        user.setPhoneNumber("+573015026458");
        user.setBirthDate(java.time.LocalDate.of(1993, 12, 15));
        user.setEmail("jose@example.com");
        user.setPassword("123456");

        role = new Role();
        role.setId(1L);
        role.setName("ADMINISTRADOR");
    }

    @Test
    void FoundRoleByName() {
        when(rolePersistencePort.findByName("ADMINISTRADOR")).thenReturn(Optional.of(role));

        Role result = roleUseCase.findByName("ADMINISTRADOR").orElseThrow();

        verify(rolePersistencePort).findByName("ADMINISTRADOR");
        assertNotNull(result);
        assertEquals("ADMINISTRADOR", result.getName());
    }


    @Test
    void userExists_ReturnsUser() {
        Long userId = 1L;
        when(userPersistencePort.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> result = userUseCase.getUserById(userId);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userPersistencePort).findById(userId);
    }

    @Test
    void userDoesNotExist() {
        when(userPersistencePort.findById(99L)).thenReturn(Optional.empty());

        Optional<User> result = userUseCase.getUserById(99L);

        assertTrue(result.isEmpty());
        verify(userPersistencePort).findById(99L);
    }
}

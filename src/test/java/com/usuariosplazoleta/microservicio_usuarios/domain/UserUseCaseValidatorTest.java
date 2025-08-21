package com.usuariosplazoleta.microservicio_usuarios.domain;

import com.usuariosplazoleta.microservicio_usuarios.domain.exception.DomainException;
import com.usuariosplazoleta.microservicio_usuarios.domain.model.User;
import com.usuariosplazoleta.microservicio_usuarios.domain.usecase.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseValidatorTest {

    private User user;

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
    }

    @Test
    void throwWhenNameIsNull() {
        user.setName(null);
        DomainException ex = assertThrows(DomainException.class, () -> UserValidator.validateUserOwner(user));
        assertTrue(ex.getMessage().contains("Nombre"));
    }

    @Test
    void throwWhenNameIsBlank() {
        user.setName(" ");
        DomainException ex = assertThrows(DomainException.class, () -> UserValidator.validateUserOwner(user));
        assertTrue(ex.getMessage().contains("Nombre"));
    }

    @Test
    void throwWhenLastNameIsBlank() {
        user.setLastName(" ");
        DomainException ex = assertThrows(DomainException.class, () -> UserValidator.validateUserOwner(user));
        assertTrue(ex.getMessage().contains("Apellido"));
    }

    @Test
    void throwWhenDocumentIsNull() {
        user.setDocument(null);
        DomainException ex = assertThrows(DomainException.class, () -> UserValidator.validateUserOwner(user));
        assertTrue(ex.getMessage().contains("Documento"));
    }

    @Test
    void throwWhenDocumentHasLetters() {
        user.setDocument("12A34");
        DomainException ex = assertThrows(DomainException.class, () -> UserValidator.validateUserOwner(user));
        assertEquals("Documento inválido", ex.getMessage());
    }

    @Test
    void throwWhenPhoneIsBlank() {
        user.setPhoneNumber(" ");
        DomainException ex = assertThrows(DomainException.class, () -> UserValidator.validateUserOwner(user));
        assertTrue(ex.getMessage().contains("Celular"));
    }

    @Test
    void throwWhenPhoneIsInvalid() {
        user.setPhoneNumber("123ABC");
        DomainException ex = assertThrows(DomainException.class, () -> UserValidator.validateUserOwner(user));
        assertEquals("Celular inválido", ex.getMessage());
    }

    @Test
    void throwWhenBirthDateIsNull() {
        user.setBirthDate(null);
        DomainException ex = assertThrows(DomainException.class, () -> UserValidator.validateUserOwner(user));
        assertTrue(ex.getMessage().contains("Fecha de nacimiento"));
    }

    @Test
    void throwWhenUserIsUnderage() {
        user.setBirthDate(LocalDate.now().minusYears(17));
        DomainException ex = assertThrows(DomainException.class, () -> UserValidator.validateUserOwner(user));
        assertEquals("El usuario debe ser mayor de edad", ex.getMessage());
    }

    @Test
    void throwWhenEmailIsBlank() {
        user.setEmail(" ");
        DomainException ex = assertThrows(DomainException.class, () -> UserValidator.validateUserOwner(user));
        assertTrue(ex.getMessage().contains("Correo"));
    }

    @Test
    void throwWhenEmailIsInvalid() {
        user.setEmail("invalid-email");
        DomainException ex = assertThrows(DomainException.class, () -> UserValidator.validateUserOwner(user));
        assertEquals("Correo inválido", ex.getMessage());
    }

    @Test
    void throwWhenPasswordIsBlank() {
        user.setPassword(" ");
        DomainException ex = assertThrows(DomainException.class, () -> UserValidator.validateUserOwner(user));
        assertTrue(ex.getMessage().contains("Clave"));
    }
}



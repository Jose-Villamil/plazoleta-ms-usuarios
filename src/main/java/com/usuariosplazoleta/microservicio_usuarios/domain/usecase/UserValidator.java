package com.usuariosplazoleta.microservicio_usuarios.domain.usecase;

import com.usuariosplazoleta.microservicio_usuarios.domain.exception.DomainException;
import com.usuariosplazoleta.microservicio_usuarios.domain.model.User;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

import static com.usuariosplazoleta.microservicio_usuarios.domain.util.DomainMessages.*;
import static com.usuariosplazoleta.microservicio_usuarios.domain.util.ValidationPatterns.*;

public class UserValidator {

    private UserValidator() {
    }

    public static void validateUserOwner(User user) {
        validateEmployee(user);
        requireNonNull(user.getBirthDate(), String.format(FIELD_REQUIRED,"Fecha de nacimiento"));
        validateAge(user.getBirthDate());
    }

    public static void validateEmployee(User user) {
        validateUser(user);
    }

    public static void validateClient(User user) {
        validateUser(user);
    }

    public static void validateUser(User user) {
        requireNonBlank(user.getName(), String.format(FIELD_REQUIRED,"Nombre"));
        requireNonBlank(user.getLastName(), String.format(FIELD_REQUIRED,"Apellido"));
        requireNonBlank(user.getDocument(), String.format(FIELD_REQUIRED,"Documento"));
        validatePattern(user.getDocument(), DOCUMENT_PATTERN, String.format(FIELD_INVALID,"Documento"));
        requireNonBlank(user.getPhoneNumber(), String.format(FIELD_REQUIRED,"Celular"));
        validatePattern(user.getPhoneNumber(), PHONE_PATTERN, String.format(FIELD_INVALID,"Celular"));
        requireNonBlank(user.getEmail(), String.format(FIELD_REQUIRED,"Correo"));
        validatePattern(user.getEmail(), EMAIL_PATTERN, String.format(FIELD_INVALID,"Correo"));
        requireNonBlank(user.getPassword(), String.format(FIELD_REQUIRED,"Clave"));
    }

    private static void requireNonBlank(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new DomainException(fieldName);
        }
    }

    private static void requireNonNull(LocalDate value, String fieldName) {
        if (value == null) {
            throw new DomainException(fieldName);
        }
    }

    private static void validatePattern(String value, Pattern pattern, String message) {
        if (value == null || !pattern.matcher(value).matches()) {
            throw new DomainException(message);
        }
    }

    private static void validateAge(LocalDate birthDate) {
        if (Period.between(birthDate, LocalDate.now()).getYears() < 18) {
            throw new DomainException(USER_LEGAL_AGE);
        }
    }
}

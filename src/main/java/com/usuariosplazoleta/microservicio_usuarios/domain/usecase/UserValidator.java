package com.usuariosplazoleta.microservicio_usuarios.domain.usecase;

import com.usuariosplazoleta.microservicio_usuarios.domain.exception.DomainException;
import com.usuariosplazoleta.microservicio_usuarios.domain.model.User;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public class UserValidator {

   // private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9]{1,13}$");
    private static final Pattern DOCUMENT_PATTERN = Pattern.compile("^[0-9]+$");

    private UserValidator() {
    }

    public static void validate(User user) {
        requireNonBlank(user.getName(), "Nombre");
        requireNonBlank(user.getLastName(), "Apellido");
        requireNonBlank(user.getDocument(), "Documento de identidad");
        validatePattern(user.getDocument(), DOCUMENT_PATTERN, "Documento inválido");
        requireNonBlank(user.getPhoneNumber(), "Celular");
        validatePattern(user.getPhoneNumber(), PHONE_PATTERN, "Teléfono inválido");
        requireNonNull(user.getBirthDate(), "Fecha de nacimiento");
        validateAge(user.getBirthDate());
        requireNonBlank(user.getEmail(), "Correo");
        validatePattern(user.getEmail(), EMAIL_PATTERN, "Correo inválido");
        requireNonBlank(user.getPassword(), "Clave");
    }

    private static void requireNonBlank(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new DomainException(String.format("El campo %s no puede ser nulo o vacío", fieldName));
        }
    }

    private static void requireNonNull(LocalDate value, String fieldName) {
        if (value == null) {
            throw new DomainException(String.format("El campo %s no puede ser nulo", fieldName));
        }
    }

    private static void validatePattern(String value, Pattern pattern, String message) {
        if (value == null || !pattern.matcher(value).matches()) {
            throw new DomainException(message);
        }
    }

    private static void validateAge(LocalDate birthDate) {
        if (Period.between(birthDate, LocalDate.now()).getYears() < 18) {
            throw new DomainException("El usuario debe ser mayor de edad");
        }
    }
}

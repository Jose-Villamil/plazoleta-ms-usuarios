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

    public static void validate(User user) {
        requireNonBlank(user.getName(), NAME_REQUIRED);
        requireNonBlank(user.getLastName(), LAST_REQUIRED);
        requireNonBlank(user.getDocument(), DOCUMENT_REQUIRED);
        validatePattern(user.getDocument(), DOCUMENT_PATTERN, INVALID_DOCUMENT);
        requireNonBlank(user.getPhoneNumber(), NUMBER_PHONE_REQUIRED);
        validatePattern(user.getPhoneNumber(), PHONE_PATTERN, INVALID_PHONE);
        requireNonNull(user.getBirthDate(), BIRTH_DATE_REQUIRED);
        validateAge(user.getBirthDate());
        requireNonBlank(user.getEmail(), EMAIL_REQUIRED);
        validatePattern(user.getEmail(), EMAIL_PATTERN, INVALID_EMAIL);
        requireNonBlank(user.getPassword(), PASSWORD_REQUIRED);
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

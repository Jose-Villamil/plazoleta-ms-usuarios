package com.usuariosplazoleta.microservicio_usuarios.domain.util;

import java.util.regex.Pattern;

public class ValidationPatterns {
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    public static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9]{1,13}$");
    public static final Pattern DOCUMENT_PATTERN = Pattern.compile("^[0-9]+$");
}

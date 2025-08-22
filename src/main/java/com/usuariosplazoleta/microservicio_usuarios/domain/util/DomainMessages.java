package com.usuariosplazoleta.microservicio_usuarios.domain.util;

public final class DomainMessages {

    private DomainMessages() {}

    public static final String FIELD_REQUIRED = "El campo %s no puede ser nulo o vacío";
    public static final String FIELD_INVALID = "%s inválido";
    public static final String USER_LEGAL_AGE = "El usuario debe ser mayor de edad";
    public static final String USER_DOESNOT_HAVE_ROL = "Los usuarios con el rol %s no tienen permiso para realizar esta acción";
    public static final String ROLE_NOT_FOUND = "El rol %s no existe";
    public static final String EMPLOYEE_CREATED_WHIT_ERROR = "Falló el servicio de restaurantes erro: %s";
}

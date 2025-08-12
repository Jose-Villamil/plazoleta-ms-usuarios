package com.usuariosplazoleta.microservicio_usuarios.domain.util;

public final class DomainMessages {

    private DomainMessages() {}

    public static final String NAME_REQUIRED = "El campo Nombre no puede ser nulo o vacío";
    public static final String LAST_REQUIRED = "El campo Apellido no puede ser nulo o vacío";
    public static final String DOCUMENT_REQUIRED = "El campo Documento de identidad no puede ser nulo o vacío";
    public static final String NUMBER_PHONE_REQUIRED = "El campo Celular no puede ser nulo o vacío";
    public static final String BIRTH_DATE_REQUIRED = "El campo Fecha de nacimiento no puede ser nulo o vacío";
    public static final String USER_LEGAL_AGE = "El usuario debe ser mayor de edad";
    public static final String EMAIL_REQUIRED = "El campo Correo no puede ser nulo o vacío";
    public static final String PASSWORD_REQUIRED = "El campo Clave no puede ser nulo o vacío";

    //public static final String USER_DOESNOT_HAVE_ROL = "El usuario no tiene el rol: ";

    public static final String INVALID_DOCUMENT = "Documento inválido";
    public static final String INVALID_PHONE = "Celular inválido";
    public static final String INVALID_EMAIL = "Correo invalido";

}

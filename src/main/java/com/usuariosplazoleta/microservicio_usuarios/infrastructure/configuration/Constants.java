package com.usuariosplazoleta.microservicio_usuarios.infrastructure.configuration;

public class Constants {

    private Constants() {}

    public static final String MESSAGE = "message";
    public static final String USER_CREATED = "Usuario creado correctamente";

    public static final String ERROR_DUPLICATE_RECORD = "El registro ya existe o viola una restricción de integridad.";
    public static final String ERROR_MICROSERVICE_COMMUNICATION = "Error en comunicación con otro servicio.";
}

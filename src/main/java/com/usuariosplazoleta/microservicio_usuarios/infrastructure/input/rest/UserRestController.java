package com.usuariosplazoleta.microservicio_usuarios.infrastructure.input.rest;

import com.usuariosplazoleta.microservicio_usuarios.application.dto.request.UserClientRequestDto;
import com.usuariosplazoleta.microservicio_usuarios.application.dto.request.UserEmployeeRequestDto;
import com.usuariosplazoleta.microservicio_usuarios.application.dto.request.UserRequestDto;
import com.usuariosplazoleta.microservicio_usuarios.application.dto.response.UserResponseDto;
import com.usuariosplazoleta.microservicio_usuarios.application.handler.IUserHandler;
import com.usuariosplazoleta.microservicio_usuarios.infrastructure.configuration.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserRestController {
    private final IUserHandler userHandler;

    @Operation(summary = "Crear usuarios propietarios",
            description = "Crea un nuevo propietario",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Creado"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "401", description = "Autenticación requerida o token inválido/expirado."),
            @ApiResponse(responseCode = "403", description = "No tienes permisos para realizar esta acción. Verifica tu rol o inicia sesión con una cuenta autorizada."),
            @ApiResponse(responseCode = "409", description = "El registro ya existe o viola una restricción de integridad.")
    })
    @PostMapping("/saveUser")
    public ResponseEntity<Map<String,String>> saveUser(@RequestBody UserRequestDto userRequestDto) {
        userHandler.saveUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.MESSAGE, Constants.USER_CREATED));
    }

    @Operation(summary = "Obtener usuario por ID",
            description = "Retorna la información de un usuario según su identificador",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "401", description = "Autenticación requerida o token inválido/expirado."),
            @ApiResponse(responseCode = "403", description = "No tienes permisos para realizar esta acción."),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userHandler.getUserById(id));
    }

    @Operation(summary = "Crear usuarios empleados",
            description = "Crea un nuevo empleado",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Creado"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "401", description = "Autenticación requerida o token inválido/expirado."),
            @ApiResponse(responseCode = "403", description = "No tienes permisos para realizar esta acción. Verifica tu rol o inicia sesión con una cuenta autorizada."),
            @ApiResponse(responseCode = "409", description = "El registro ya existe o viola una restricción de integridad.")
    })
    @PostMapping("/saveUserEmployee")
    public ResponseEntity<Map<String,String>> saveUserEmployee(@RequestBody UserEmployeeRequestDto userEmployeeRequestDto) {
        userHandler.saveUserEmployee(userEmployeeRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.MESSAGE, Constants.USER_CREATED));
    }

    @Operation(summary = "Registrar clientes",
            description = "Crea un nuevo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Creado"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "409", description = "El registro ya existe o viola una restricción de integridad.")
    })
    @PostMapping("/saveClients")
    public ResponseEntity<Map<String,String>> registerClient(@RequestBody UserClientRequestDto userClientRequestDto) {
        userHandler.saveUserClient(userClientRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.MESSAGE, Constants.USER_CREATED));
    }

}

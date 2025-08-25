package com.usuariosplazoleta.microservicio_usuarios.infrastructure.input.rest;

import com.usuariosplazoleta.microservicio_usuarios.application.dto.request.LoginRequest;
import com.usuariosplazoleta.microservicio_usuarios.application.dto.response.LoginResponse;
import com.usuariosplazoleta.microservicio_usuarios.application.handler.IAuthHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthHandler authHandler;

    @Operation(summary = "Iniciar sesión",
            description = "Autentica a un usuario con sus credenciales y devuelve un token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
        LoginResponse response = authHandler.login(req);
        return ResponseEntity.ok().body(response);
    }

}


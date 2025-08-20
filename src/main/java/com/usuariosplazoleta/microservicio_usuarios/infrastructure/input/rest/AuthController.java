package com.usuariosplazoleta.microservicio_usuarios.infrastructure.input.rest;

import com.usuariosplazoleta.microservicio_usuarios.application.dto.request.LoginRequest;
import com.usuariosplazoleta.microservicio_usuarios.application.dto.response.LoginResponse;
import com.usuariosplazoleta.microservicio_usuarios.application.handler.IAuthHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthHandler authHandler;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login( @RequestBody LoginRequest req) {
       LoginResponse response = authHandler.login(req);
        return ResponseEntity.ok().body(response);
    }
}


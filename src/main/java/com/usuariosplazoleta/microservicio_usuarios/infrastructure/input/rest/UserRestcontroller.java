package com.usuariosplazoleta.microservicio_usuarios.infrastructure.input.rest;

import com.usuariosplazoleta.microservicio_usuarios.application.dto.request.UserRequestDto;
import com.usuariosplazoleta.microservicio_usuarios.application.dto.response.UserResponseDto;
import com.usuariosplazoleta.microservicio_usuarios.application.handler.IUserHandler;
import com.usuariosplazoleta.microservicio_usuarios.infrastructure.configuration.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserRestcontroller {
    private final IUserHandler userHandler;

    @PostMapping("/saveUser")
    public ResponseEntity<Map<String,String>> saveUser(@RequestBody UserRequestDto userRequestDto) {
        userHandler.saveUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.MESSAGE, Constants.USER_CREATED));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userHandler.getUserById(id));
    }

}

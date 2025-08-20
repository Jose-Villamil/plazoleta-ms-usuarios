package com.usuariosplazoleta.microservicio_usuarios.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usuariosplazoleta.microservicio_usuarios.application.dto.request.LoginRequest;
import com.usuariosplazoleta.microservicio_usuarios.application.handler.IUserHandler;
import com.usuariosplazoleta.microservicio_usuarios.application.handler.IAuthHandler;
import com.usuariosplazoleta.microservicio_usuarios.application.dto.response.LoginResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "security.jwt.secret=clavesecretaparamicroserviciosdeplazoletadedecomidas"
})
class SecurityIntegrationTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper om;

    @MockitoBean
    private IUserHandler userHandler;
    @MockitoBean private IAuthHandler authHandler;

    private Key key;

    @BeforeEach
    void setup() {
        byte[] secret = "clavesecretaparamicroserviciosdeplazoletadedecomidas".getBytes(StandardCharsets.UTF_8);
        key = Keys.hmacShaKeyFor(secret);

        Mockito.doNothing().when(userHandler).saveUser(any());
    }

    private String jwt(String email, Long uid, List<String> roles, long minutesValid) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(email)
                .claim("uid", uid)
                .claim("roles", roles)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + minutesValid * 60_000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    private static String bearer(String token) { return "Bearer " + token; }

    @Test
    void login_ok_devuelve_token_200() throws Exception {
        Mockito.when(authHandler.login(any(LoginRequest.class)))
                .thenReturn(new LoginResponse(120, "DUMMY_TOKEN"));

        var body = Map.of("email","admin@plazoleta.com","password","admin123");
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("DUMMY_TOKEN"));
    }

    @Test
    void login_credenciales_invalidas_ret_401() throws Exception {
        Mockito.when(authHandler.login(any(LoginRequest.class)))
                .thenThrow(new BadCredentialsException("bad"));

        var body = Map.of("email","bad@plazoleta.com","password","wrong");
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(body)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void crear_propietario_con_admin_ret_201() throws Exception {
        String token = jwt("admin@mail.com", 1L, List.of("ROLE_ADMINISTRADOR"), 10);
        mockMvc.perform(post("/api/v1/users/saveUser")
                        .header("Authorization", bearer(token))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isCreated());
    }

    @Test
    void crear_empleado_con_owner_ret_201() throws Exception {
        String token = jwt("owner@mail.com", 5L, List.of("ROLE_PROPIETARIO"), 10);
        mockMvc.perform(post("/api/v1/users/saveUser")
                        .header("Authorization", bearer(token))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isCreated());
    }
}


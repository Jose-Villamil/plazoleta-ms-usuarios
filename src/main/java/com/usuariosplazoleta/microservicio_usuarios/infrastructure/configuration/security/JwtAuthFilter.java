package com.usuariosplazoleta.microservicio_usuarios.infrastructure.configuration.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        var header = req.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            var token = header.substring(7);
            try {
                var claims = jwtProvider.parse(token).getBody();
                String email = claims.getSubject();
                @SuppressWarnings("unchecked")
                var roles = ((List<Object>) claims.get("roles"))
                        .stream().map(Object::toString).map(SimpleGrantedAuthority::new).toList();

                // No cargamos desde BD; usamos los claims.
                var auth = new UsernamePasswordAuthenticationToken(email, null, roles);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception ignored) { /* token inválido/expirado: sigue anónimo */ }
        }
        chain.doFilter(req, res);
    }
}


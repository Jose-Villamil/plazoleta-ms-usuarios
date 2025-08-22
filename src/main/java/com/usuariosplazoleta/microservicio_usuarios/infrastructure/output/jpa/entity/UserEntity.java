package com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable = false)
    private String name;
    @Column(name = "apellido", nullable = false)
    private String lastName;
    @Column(name = "numero_documento", nullable = false, unique = true)
    private String document;
    @Column(name = "celular", nullable = false)
    private String phoneNumber;
    @Column(name = "fecha_nacimiento")
    private LocalDate birthDate;
    @Column(name = "correo", nullable = false, unique = true)
    private String email;
    @Column(name = "clave", nullable = false)
    private String password;
    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false)
    private RoleEntity role;
}


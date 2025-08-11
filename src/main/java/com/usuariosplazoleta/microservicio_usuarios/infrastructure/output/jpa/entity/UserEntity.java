package com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre")
    private String name;
    @Column(name = "apellido")
    private String lastName;
    @Column(name = "numero_documento")
    private String document;
    @Column(name = "celular")
    private String phoneNumber;
    @Column(name = "fecha_nacimiento")
    private LocalDate birthDate;
    @Column(name = "correo")
    private String email;
    @Column(name = "clave")
    private String password;
    @ManyToOne
    @JoinColumn(name = "id_role")
    private RoleEntity role;

}


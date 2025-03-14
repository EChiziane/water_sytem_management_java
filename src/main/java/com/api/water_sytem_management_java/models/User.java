package com.api.water_sytem_management_java.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


import java.time.LocalDateTime;
import java.util.UUID;




@Entity
@Table(name = "tb_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "O nome não pode estar vazio")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "O nome de usuário não pode estar vazio")
    @Column(unique = true, nullable = false)
    private String userName;

    @Email(message = "O e-mail deve ser válido")
    @NotBlank(message = "O e-mail não pode estar vazio")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "A senha não pode estar vazia")
    @JsonIgnore // Esconde a senha na API
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private UserRole role = UserRole.NORMAL;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private Boolean isActive = true;

    public User( String name, String userName, String email, String password) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public User() {

    }



}

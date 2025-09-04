package com.josewynder.neoapp.clientapi.security.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "{field.username.required}")
    private String username;

    @Column(nullable = false)
    @NotEmpty(message = "{field.password.required}")
    private String password;

    @Column(name = "is_admin")
    private boolean isAdmin;
}

package com.vodafone.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name="User")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    private String password;

    private boolean enabled;

    @NotBlank
    private String name;

    private String verficationCode;

    private int loginTries = 0;
    private boolean locked;

    public abstract String getRole();
}

package com.vodafone.ecommerce.model;

import jakarta.persistence.*;


@Entity
@Table(name = "admin")

public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}

package com.vodafone.ecommerce.model;

import jakarta.persistence.*;

@Entity
@Table(name = "product")

public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    private Double price;
    private Integer stock;
    private Double rating;
    private String image;
}

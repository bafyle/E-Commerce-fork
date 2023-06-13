package com.vodafone.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @NotNull
    @Min(value = 1, message = "Price can't be less than 1")
    private Double price;

    @NotNull
    @Min(value = 0, message = "Stock can't be less than 0")
    private Integer stock;

    @Min(value = 0, message = "Rating can't be less than 0")
    @Max(value = 5, message = "Rating can't be more than 5")
    private Double rating;

    @NotBlank(message = "")
    private String image;

    @NotNull
    private Boolean isArchived;
}

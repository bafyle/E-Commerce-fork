package com.vodafone.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "cart")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //todo: consider PrimaryKeyJoinColumn
    @OneToOne(mappedBy = "cart")
    @JsonIgnore
    private Customer customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private Set<CartItem> cartItems;

    private String address;

    @JsonProperty("totalPrice")
    public Double getTotalPrice() {
        double sum = 0;
        for (CartItem cartItem:
             cartItems) {
            if (cartItem.getProduct().getIsArchived()) {
                continue;
            }
            sum += (cartItem.getQuantity()*cartItem.getProduct().getPrice());
        }
        return sum;
    }
}

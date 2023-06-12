package com.vodafone.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Entity(name ="Customer")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@PrimaryKeyJoinColumn(referencedColumnName="id")
public class Customer extends User
{
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Address> addresses;

    @OneToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Order> orders;

    @Override
    public String getRole() {
        return "Customer";
    }
}

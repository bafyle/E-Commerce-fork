package com.vodafone.ecommerce.model;

import jakarta.persistence.*;
import lombok.Setter;


@Entity(name = "Admin")
@Setter
@PrimaryKeyJoinColumn(referencedColumnName="id")
public class Admin extends User{

    @Override
    public String getRole() {
        return "Admin";
    }
}

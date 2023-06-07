package com.vodafone.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vodafone.ecommerce.model.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long>
{
    Optional<User> findByEmail(String email);
}

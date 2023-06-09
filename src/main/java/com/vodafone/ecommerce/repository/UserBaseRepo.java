package com.vodafone.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.vodafone.ecommerce.model.User;

@NoRepositoryBean
public interface UserBaseRepo<T extends User> extends JpaRepository<T, Long>
{
    Optional<T> findByEmail(String email);
}

package com.vodafone.ecommerce.repository;

import com.vodafone.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>
{
    Page<Product> findByNameContainsIgnoreCaseAndCategoryId(Pageable pageable, String name, Long categoryId);

    Page<Product> findByCategoryId(Pageable pageable, Long CategoryId);

    Page<Product> findByNameContainsIgnoreCase(Pageable pageable, String name);

    Optional<Product> findByName(String name);
}

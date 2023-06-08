package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.controller.ProductController;
import com.vodafone.ecommerce.exception.DuplicateEntityException;
import com.vodafone.ecommerce.exception.NotFoundException;
import com.vodafone.ecommerce.model.Category;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.repository.CategoryRepo;
import com.vodafone.ecommerce.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ProductService {

    private ProductRepo productRepo;
    private CategoryRepo categoryRepo;

    @Autowired
    public ProductService(ProductRepo productRepo, CategoryRepo categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

    public List<Product> getAllProducts(String name, String category) {
        return productRepo.findAll();
    }

    public Product getProductById(Long id) {
        Optional<Product> product = productRepo.findById(id);
        if (product.isEmpty()) {
            throw new NotFoundException("Product with this id not found.");
        }
        return product.get();
    }

    public Product addProduct(Product product) {
        if (productRepo.findByName(product.getName()).isPresent()) {
            throw new DuplicateEntityException("Product with same name already exists.");
        }

        //TODO: cat service instead of repo?
        if (categoryRepo.findById(product.getCategory().getId()).isEmpty()) {
            throw new NotFoundException("Category not found."); // TODO: not found or different exception?
        }

        // TODO: handle image
        return productRepo.save(product);
    }

    public Product updateProduct(Product product, Long id) {
        if (productRepo.findById(id).isEmpty()) {
            throw new NotFoundException("Product id not found.");
        }

        product.setId(id);
        return productRepo.save(product);
    }
    
    public void deleteProduct(Long id){
        if (productRepo.findById(id).isEmpty()) {
            throw new NotFoundException("Product id not found.");
        }
        productRepo.deleteById(id);
    }
    //TODO: links with hateoas
}

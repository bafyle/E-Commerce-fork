package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.model.Category;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.repository.CategoryRepo;
import com.vodafone.ecommerce.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
        return productRepo.findById(id).orElse(null);
    }

    public Product addProduct(String name, String category, Double price, Integer stock, MultipartFile image) {
        Product productByName = productRepo.findByName(name).orElse(null);

        if (productByName!=null) {
            throw RuntimeException
        }

        // TODO: handle image path
        String imagePath = "imagepath";
        Category categoryByName = categoryRepo.findByName(category).orElse(null);
        Product product = new Product(null, name, categoryByName, price, stock, null, imagePath);

        return productRepo.save(product);
    }
}

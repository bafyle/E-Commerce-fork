package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

// Homepage - All Products
// Product Details
// Add Product - Admin
// CartList

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "category", required = false) String category) {
        return new ResponseEntity<>(productService.getAllProducts(name, category), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Product> getProductById(@RequestParam(name = "id") Long id) {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> addProduct() { //TODO: handle image
        Product product = productService.addProduct(name, category, price, stock, image);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "price", required = false) Double price,
            @RequestParam(name = "stock", required = false) Integer stock,
            @RequestParam(name = "image", required = false) MultipartFile image
    ) {
        Product product = productService.updateProduct(name, category, price, stock, image);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}

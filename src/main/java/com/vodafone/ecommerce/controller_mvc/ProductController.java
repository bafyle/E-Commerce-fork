package com.vodafone.ecommerce.controller_mvc;

import com.vodafone.ecommerce.model.Category;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.service.CategoryService;
import com.vodafone.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Homepage - All Products
// Product Details
// Add Product - Admin
// CartList

//@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    //TODO: page size according to spring profile?
    @GetMapping
    public String getAllProducts(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "category", required = false) Long categoryId, Model model) { //TODO: cat_id?
        List<Product> allProducts = productService.getAllProducts(page, size, name, categoryId);
        List<Category> allCatgories = categoryService.getAllCategories();
        model.addAttribute("product_index");
        model.addAttribute("all_products", allProducts);
        model.addAttribute("all_categories", allCatgories);
//        ModelAndView modelAndView = new ModelAndView("product_index");
//        modelAndView.addObject("all_products",allProducts);
//        modelAndView.addObject("all_categories",allCatgories);
        return "product_index";
    }

    @GetMapping(value = "/{id}")
    public String getProductById(@PathVariable(name = "id") Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product_single_index";
    }

    @GetMapping("/add")
    public String addProduct(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "admin-addProduct";
    }

    @PostMapping("/add")
    public String addNewProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult, Model model) {

        List<Category> allCategories = categoryService.getAllCategories();
        if (bindingResult.hasErrors()) {
            model.addAttribute("product", product);
            model.addAttribute("all_categories",allCategories);
            return "admin-addProduct";
        }


        productService.addProduct(product);
        return "redirect:/admin/products";
    }

    //
    @GetMapping("/{id}/edit")
    public String updateProduct(@PathVariable("id") Long productId, Model model) {
        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);
        return "admin-editProduct";
    }

    @PostMapping("/{id}/edit")
    public String updateProduct(@PathVariable("id") Long productId, @Valid @ModelAttribute("product") Product product,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("product", product);
            return "admin-editProduct";
        }
        productService.updateProduct(product, productId);
        return "redirect:/" + productId + "/products";
    }

    @DeleteMapping(value = "/{id}/delete")
    public String deleteProduct(@PathVariable(name = "id") Long id, Model model) {
        productService.deleteProduct(id);
        return "redirect:/product";
    }
}

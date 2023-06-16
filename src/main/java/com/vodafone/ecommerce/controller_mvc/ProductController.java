package com.vodafone.ecommerce.controller_mvc;

import com.vodafone.ecommerce.model.Category;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.model.SecurityUser;
import com.vodafone.ecommerce.model.dto.CartItemDTO;
import com.vodafone.ecommerce.service.CategoryService;
import com.vodafone.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Homepage - All Products
// Product Details
// Add Product - Admin
// CartList

@Controller
@RequestMapping("/product")
@PreAuthorize("hasAuthority('Admin')")
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
    @PreAuthorize("hasAnyAuthority('Admin','Customer')")
    public String getAllProducts(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "category", required = false) Long categoryId, Model model) { //TODO: cat_id?

        Product p1 = new Product();
        p1.setName("Product Dummy #1");
        p1.setImage("img");
        p1.setPrice(4D);
        p1.setStock(50);
        // p1.setCategory(categoryService.getCategoryById(3L));

     //   productService.addProduct(p1);


        List<Product> allProducts = productService.getAllProducts(page, size, name, categoryId);
        List<Category> allCatgories = categoryService.getAllCategories();
        model.addAttribute("all_products", allProducts);
        model.addAttribute("all_categories", allCatgories);
        return "admin-productsRead";
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('Admin','Customer')")
    public String getProductById(@PathVariable(name = "id") Long id, @AuthenticationPrincipal SecurityUser user, Model model) {
        Product product = productService.getProductById(id);
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setProductId(id);
        cartItemDTO.setQuantity(1);
        model.addAttribute("product", product);
        model.addAttribute("customerId", user.getUser().getId());
        model.addAttribute("cartItem", cartItemDTO);
        return "admin-productDetailsWithAdminOptions";
    }
    @PostMapping(value="/search")
    @PreAuthorize("hasAnyAuthority('Admin','Customer')")
    public String searchForProduct(
        @RequestParam(value="searchBy") String searchType, 
        @RequestParam(value="searchValue") String searchValue,
        Model model)
    {
        List<Product> allProducts = null;
        if(searchType.equals("category"))
        {
            var cat = categoryService.getCategoryByNameIgnoreCases(searchValue);
            allProducts = productService.getAllProducts(0, 100, null, cat.getId());
        }
        else
        {
            allProducts = productService.getAllProducts(0, 100, searchValue, null);
        }
        model.addAttribute("all_products", allProducts);
        
        return "admin-productsRead";
    }

    @GetMapping("/add")
    public String addProduct(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        model.addAttribute("all_categories",categoryService.getAllCategories());
        return "admin-productCreate";
    }

    @PostMapping("/add")
    public String addNewProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult, Model model) {

        List<Category> allCategories = categoryService.getAllCategories();
        if (bindingResult.hasErrors()) {
            model.addAttribute("product", product);
            model.addAttribute("all_categories",allCategories);
            return "admin-productCreate";
        }


        productService.addProduct(product);
        return "redirect:/product";
    }

    //
    @GetMapping("/{id}/edit")
    public String updateProduct(@PathVariable("id") Long productId, Model model) {
        Product product = productService.getProductById(productId);
        model.addAttribute("productId", productId);
        model.addAttribute("product", product);
        model.addAttribute("all_categories",categoryService.getAllCategories());
        return "admin-productEdit";
    }

    @PostMapping("/{id}/edit")
    public String updateProduct(@PathVariable("id") Long productId, @Valid @ModelAttribute("product") Product product,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("product", product);
            return "admin-productEdit";
        }
        productService.updateProduct(product, productId);
        return "redirect:/product";
    }

    @GetMapping(value = "/{id}/delete")
    public String deleteProduct(@PathVariable(name = "id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/product";
    }
}

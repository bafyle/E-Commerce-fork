package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.model.Category;
import com.vodafone.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/category")
@PreAuthorize("hasAuthority('Admin')")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('Customer','Admin')")
    public ResponseEntity<List<Category>> getAllCategories() {
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('Customer','Admin')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(categoryService.getCategoryById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Category> addCategory(@RequestBody Category category) { //TODO: handle image
        Category categoryRes = categoryService.addCategory(category);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category, @PathVariable(name = "id") Long id) {
        Category categoryRes = categoryService.updateCategory(category, id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable(name = "id") Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

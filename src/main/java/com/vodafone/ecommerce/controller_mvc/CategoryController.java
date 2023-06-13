package com.vodafone.ecommerce.controller_mvc;

import com.vodafone.ecommerce.model.Admin;
import com.vodafone.ecommerce.model.Category;
import com.vodafone.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
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
    public String getAllCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("all_categories", categories);
        return "admin-categoriesRead";
    }

    @PreAuthorize("hasAnyAuthority('Customer','Admin')")
    @GetMapping(value = "/{id}")
    public String getCategoryById(@PathVariable(name = "id") Long id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "admin-categoryRead";
    }

    @GetMapping("/add")
    public String addCategory(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "admin-categoryAdd";
    }

    @PostMapping
    public String addCategory(@Valid
                              @ModelAttribute("category")
                              Category category, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("category", category);
            return "admin-categoryAdd";
        }

        categoryService.addCategory(category);
        return "redirect:/category";
    }

    @GetMapping("/{id}/edit")
    public String updateCategory(@PathVariable(name = "id") Long id,
                                 Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "admin-categoryEdit";
    }

    @PostMapping(value = "/{id}/edit")
    public String updateCategory(@PathVariable(name = "id") Long categoryId,
                                 @Valid @ModelAttribute Category category,
                                 BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("category", category);
            return "admin-categoryEdit";
        }
        categoryService.updateCategory(category,categoryId);
        return "redirect:/category";
    }

    @GetMapping(value = "/{id}/delete")
    public String deleteCategory(@PathVariable(name = "id") Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/category";
    }
}

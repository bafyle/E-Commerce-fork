package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.exception.DuplicateEntityException;
import com.vodafone.ecommerce.exception.NotFoundException;
import com.vodafone.ecommerce.model.Category;
import com.vodafone.ecommerce.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepo categoryRepo;

    @Autowired
    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    public Category getCategoryById(Long id) {
        Optional<Category> category = categoryRepo.findById(id);
        if (category.isEmpty()) {
            throw new NotFoundException("Category with this id not found.");
        }
        return category.get();
    }

    public Category addCategory(Category category) {
        if (categoryRepo.findByName(category.getName()).isPresent()) {
            throw new DuplicateEntityException("Category with same name already exists.");
        }

        return categoryRepo.save(category);
    }

    public Category updateCategory(Category category, Long id) {
        if (categoryRepo.findById(id).isEmpty()) {
            throw new NotFoundException("Category id not found.");
        }

        category.setId(id);
        return categoryRepo.save(category);
    }

    public void deleteCategory(Long id){
        if (categoryRepo.findById(id).isEmpty()) {
            throw new NotFoundException("Category id not found.");
        }
        categoryRepo.deleteById(id);
    }
}

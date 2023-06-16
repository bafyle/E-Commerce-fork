package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.exception.DuplicateEntityException;
import com.vodafone.ecommerce.exception.NotFoundException;
import com.vodafone.ecommerce.model.Category;
import com.vodafone.ecommerce.repository.CategoryRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CategoryServiceTest {
    @InjectMocks
    CategoryService categoryService;

    @Mock
    CategoryRepo categoryRepo;

    @Test
    public void getAllCategory_SendVoid_ReturnCategoryList() {
        // Arrange
        List<Category> categories = List.of(new Category(1L, "cat1"), new Category(2L, "cat2"));

        when(categoryRepo.findAll()).thenReturn(categories);
        // Act
        List<Category> result = categoryService.getAllCategories();
        // Assert
        assertEquals(2, categories.size());
        assertInstanceOf(List.class, categories);
    }

    @Test
    public void getCategoryById_SendValidId_ReturnCategory() {
        // Arrange
        String name = "category";
        Long id = 1L;
        Category category = new Category(id, name);

        when(categoryRepo.findById(id)).thenReturn(Optional.of(category));
        // Act
        Category result = categoryService.getCategoryById(id);
        // Assert
        assertEquals(category.getName(), result.getName());
        assertEquals(category.getId(), result.getId());
    }

    @Test
    public void getCategoryById_SendInValidId_ThrowNotFoundException() {
        // Arrange
        String name = "category";
        Long id = 1L;
        Category category = new Category(null, name);

        when(categoryRepo.findById(id)).thenReturn(Optional.empty());
        // Act
        // Assert
        assertThrows(NotFoundException.class, () -> {categoryService.getCategoryById(id);});
    }

    @Test
    public void getCategoryByNameIgnoreCase_SendValidName_ReturnCategory() {
        // Arrange
        String name = "category";
        Long id = 1L;
        Category category = new Category(id, name);

        when(categoryRepo.findByNameContainsIgnoreCase(name)).thenReturn(Optional.of(category));
        // Act
        Category result = categoryService.getCategoryByNameIgnoreCases(name);
        // Assert
        assertEquals(category.getName(), result.getName());
        assertEquals(category.getId(), result.getId());
    }

    @Test
    public void getCategoryByNameIgnoreCase_SendInValidName_ThrowNotFoundException() {
        // Arrange
        String name = "category";
        Long id = 1L;
        Category category = new Category(null, name);

        when(categoryRepo.findByNameContainsIgnoreCase(name)).thenReturn(Optional.empty());
        // Act
        // Assert
        assertThrows(NotFoundException.class, () -> {categoryService.getCategoryByNameIgnoreCases(name);});
    }

    @Test
    public void addCategory_SendValidName_ReturnCategory() {
        // Arrange
        String name = "category";
        Long id = 1L;
        Category category = new Category(null, name);

        when(categoryRepo.findByName(category.getName())).thenReturn(Optional.empty());
        when(categoryRepo.save(category)).thenReturn(new Category(id, name));
        // Act
        Category result = categoryService.addCategory(category);
        // Assert
        assertEquals(category.getName(), result.getName());
        assertNotNull(result.getId());
    }

    @Test()
    public void addCategory_SendDuplicateName_ThrowDuplicateEntityException() {
        // ArrangeS
        String name = "category";
        Long id = 1L;
        Category category = new Category(null, name);

        when(categoryRepo.findByName(category.getName())).thenReturn(Optional.of(category));
        when(categoryRepo.save(category)).thenReturn(new Category(id, name));
        // Act
        assertThrows(DuplicateEntityException.class, () -> {categoryService.addCategory(category);});
    }

    @Test
    public void updateCategory_SendValidId_ReturnCategory() {
        // Arrange
        String updatedName = "category1";
        Long id = 1L;
        Category category = new Category(null, updatedName);

        when(categoryRepo.findById(id)).thenReturn(Optional.of(new Category(id, "cat1")));
        when(categoryRepo.save(category)).thenReturn(new Category(id, updatedName));
        // Act
        Category result = categoryService.updateCategory(category, id);
        // Assert
        assertEquals(updatedName, result.getName());
        assertNotNull(result.getId());
    }

    @Test()
    public void updateCategory_SendInvalidId_ThrowNotFoundException() {
        // ArrangeS
        String name = "category";
        Long id = 1L;
        Category category = new Category(null, name);

        when(categoryRepo.findById(id)).thenReturn(Optional.empty());
        // Act
        assertThrows(NotFoundException.class, () -> {categoryService.updateCategory(category, id);});
    }

    @Test
    public void deleteCategory_SendValidId_ReturnVoid() {
        // Arrange
        Long id = 1L;

        when(categoryRepo.findById(id)).thenReturn(Optional.of(new Category(id, "cat1")));
        // Act
        // Assert
        assertDoesNotThrow(() -> {categoryService.deleteCategory(id);});
    }

    @Test()
    public void deleteCategory_SendInvalidId_ThrowNotFoundException() {
        // Arrange
        Long id = 1L;

        when(categoryRepo.findById(id)).thenReturn(Optional.empty());
        // Act
        // Assert
        assertThrows(NotFoundException.class ,() -> {categoryService.deleteCategory(id);});
    }
}


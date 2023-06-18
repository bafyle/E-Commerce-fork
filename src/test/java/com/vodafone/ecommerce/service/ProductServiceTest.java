package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.exception.DuplicateEntityException;
import com.vodafone.ecommerce.exception.NotFoundException;
import com.vodafone.ecommerce.model.Category;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.repository.ProductRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    CategoryService categoryService;

    @Mock
    private ProductRepo productRepo;

    @Test
    public void getAllProduct_SendValidPageSize_ReturnProductList() {
        // Arrange
        int page = 0;
        int size = 5;
        Pageable pageable = PageRequest.of(page, size);
        when(productRepo.findAll(pageable)).thenReturn(Page.empty());
        // Act
        List<Product> result = productService.getAllProducts(0, 5, null, null);
        // Assert
        assertEquals(0, result.size());
        assertInstanceOf(List.class, result);
    }

    @Test
    public void getAllProduct_SendValidPageSizeNameCategoryId_ReturnProductList() {
        // Arrange
        int page = 0;
        int size = 5;
        Pageable pageable = PageRequest.of(page, size);
        when(categoryService.getCategoryById(1L)).thenReturn(new Category(1L, ""));
        when(productRepo.findByNameContainsIgnoreCaseAndCategoryId(pageable,"", 1L)).thenReturn(Page.empty());
        // Act
        List<Product> result = productService.getAllProducts(0, 5, "", 1L);
        // Assert
        assertEquals(0, result.size());
        assertInstanceOf(List.class, result);
    }

    @Test
    public void getAllProduct_SendValidPageSizeName_ReturnProductList() {
        // Arrange
        int page = 0;
        int size = 5;
        Pageable pageable = PageRequest.of(page, size);
        when(productRepo.findByNameContainsIgnoreCase(pageable,"")).thenReturn(Page.empty());
        // Act
        List<Product> result = productService.getAllProducts(0, 5, "", null);
        // Assert
        assertEquals(0, result.size());
        assertInstanceOf(List.class, result);
    }

    @Test
    public void getAllProduct_SendInValidPageSizeCategoryId_ReturnProductList() {
        // Arrange
        int page = -1;
        int size = 0;
        Pageable pageable = PageRequest.of(0, 10);
        when(categoryService.getCategoryById(1L)).thenReturn(new Category(1L, ""));
        when(productRepo.findByCategoryId(pageable, 1L)).thenReturn(Page.empty());
        // Act
        List<Product> result = productService.getAllProducts(-1, 0, null, 1L);
        // Assert
        assertEquals(0, result.size());
        assertInstanceOf(List.class, result);
    }

    @Test
    public void getProductById_SendValidId_ReturnProduct() {
        // Arrange
        String name = "product";
        Long id = 1L;
        Product product = new Product();
        product.setName(name);
        product.setId(id);

        when(productRepo.findById(id)).thenReturn(Optional.of(product));
        // Act
        Product result = productService.getProductById(id);
        // Assert
        assertEquals(product.getName(), result.getName());
        assertEquals(product.getId(), result.getId());
    }

    @Test
    public void getProductById_SendInValidId_ThrowNotFoundException() {
        // Arrange
        Long id = 1L;
        when(productRepo.findById(id)).thenReturn(Optional.empty());
        // Act
        // Assert
        assertThrows(NotFoundException.class, () -> {productService.getProductById(id);});
    }


    @Test
    public void addProduct_SendValidName_ReturnProduct() {
        // Arrange
        String name = "product";
        Long id = 1L;
        Product product = new Product();
        product.setName(name);
        product.setId(id);
        product.setCategory(new Category(1L, "cat"));
        product.setStock(50);
        product.setPrice(10.0);

        when(categoryService.getCategoryById(product.getCategory().getId())).thenReturn(product.getCategory());
        when(productRepo.findByName(product.getName())).thenReturn(Optional.empty());
        when(productRepo.save(product)).thenReturn(product);
        // Act
        Product result = productService.addProduct(product);
        // Assert
        assertEquals(product.getName(), result.getName());
        assertNotNull(result.getId());
    }

    @Test()
    public void addProduct_SendDuplicateName_ThrowDuplicateEntityException() {
        // Arrange
        String name = "product";
        Long id = 1L;
        Product product = new Product();

        product.setName(name);
        product.setId(id);
        product.setCategory(new Category(1L, "cat"));
        product.setStock(50);
        product.setPrice(10.0);

        when(categoryService.getCategoryById(product.getCategory().getId())).thenReturn(product.getCategory());
        when(productRepo.findByName(product.getName())).thenReturn(Optional.of(product));
        when(productRepo.save(product)).thenReturn(product);
        // Act
        // Assert
        assertThrows(DuplicateEntityException.class, () -> {productService.addProduct(product);});
    }

//    Mock returns null?
//    @Test
//    public void updateProduct_SendValidId_ReturnProduct() {
//        // Arrange
//        String updatedName = "product1";
//        Long id = 1L;
//        Product product = new Product(id, "name", null, 5.0, 1, 0.0, "", false);
//        Product product1 = new Product(id, updatedName, null, 5.0, 1, 0.0, "", false);
//
//        Product updatedProduct = new Product();
//        product.setId(id);
//        product.setName(updatedName);
//
//
//        when(productRepo.findById(id)).thenReturn(Optional.of(product));
//        when(productRepo.save(updatedProduct)).thenReturn(product1);
//        // Act
//        Product result = productService.updateProduct(product, id);
//        // Assert
//        assertEquals(updatedName, result.getName());
//    }

    @Test()
    public void updateProduct_SendInvalidId_ThrowNotFoundException() {
        // Arrange
        String updatedName = "product1";
        Long id = 1L;
        Product product = new Product();
        product.setId(id);
        product.setName("product");
        product.setStock(50);
        product.setPrice(10.0);

        Product updatedProduct = new Product();
        product.setName(updatedName);


        when(productRepo.findById(id)).thenReturn(Optional.empty());
        when(productRepo.save(product)).thenReturn(updatedProduct);
        // Act
        // Assert
        assertThrows(NotFoundException.class, () -> {productService.updateProduct(product, id);});
    }

    @Test
    public void deleteProduct_SendValidId_ReturnVoid() {
        // Arrange
        Long id = 1L;
        Product product = new Product();
        product.setIsArchived(false);
        when(productRepo.findById(id)).thenReturn(Optional.of(product));
        // Act
        // Assert
        assertDoesNotThrow(() -> {productService.deleteProduct(id);});
    }

    @Test()
    public void deleteProduct_SendInvalidId_ThrowNotFoundException() {
        // Arrange
        Long id = 1L;

        when(productRepo.findById(id)).thenReturn(Optional.empty());
        // Act
        // Assert
        assertThrows(NotFoundException.class ,() -> {productService.deleteProduct(id);});
    }
}

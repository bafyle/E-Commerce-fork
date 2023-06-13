package com.vodafone.ecommerce.model.dto;

import com.vodafone.ecommerce.model.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
class ProductDto {
    private Long id;
    @NotBlank(message = "Product Should not be Empty")
    private String name;
    @NotBlank(message = "Photo Url Should not be Empty")
    private String photoUrl;
    @NotBlank(message = "Description Should not be Empty")
    private String description;
    @NotNull(message = "Price Should not be Empty")
    private Double price;
    @NotNull(message = "Category name Should not be Null")
    private Category category;
    @Min(value = 1, message = "Min stockQuantity is 1")
    private Long stockQuantity;
}

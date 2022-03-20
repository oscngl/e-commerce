package com.osc.ecommerce.entities.dtos;

import com.osc.ecommerce.entities.concretes.Category;
import com.osc.ecommerce.entities.concretes.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    @NotEmpty(message = "Name is required!")
    @NotBlank(message = "Name is required!")
    private String name;

    @NotEmpty(message = "Description is required!")
    @NotBlank(message = "Description is required!")
    private String description;

    @NotEmpty(message = "Price is required!")
    @NotBlank(message = "Price is required!")
    private int price;

    private String photoUrl;

    @NotEmpty(message = "Category is required!")
    @NotBlank(message = "Category is required!")
    private Category category;

    @NotEmpty(message = "Supplier is required!")
    @NotBlank(message = "Supplier is required!")
    private Supplier supplier;

}

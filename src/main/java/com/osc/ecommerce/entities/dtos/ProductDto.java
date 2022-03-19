package com.osc.ecommerce.entities.dtos;

import com.osc.ecommerce.entities.concretes.Category;
import com.osc.ecommerce.entities.concretes.Supplier;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class ProductDto {

    @NotEmpty(message = "Name is required!")
    @NotBlank(message = "Name is required!")
    private String name;

    @NotEmpty(message = "Description is required!")
    @NotBlank(message = "Description is required!")
    private String description;

    private String photoUrl;

    @NotEmpty(message = "Price is required!")
    @NotBlank(message = "Price is required!")
    private int price;

    @NotEmpty(message = "Category is required!")
    @NotBlank(message = "Category is required!")
    private Category category;

    @NotEmpty(message = "Supplier is required!")
    @NotBlank(message = "Supplier is required!")
    private Supplier supplier;

}

package com.osc.ecommerce.entities.dtos;

import com.osc.ecommerce.entities.concretes.Category;
import com.osc.ecommerce.entities.concretes.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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

    @NotNull(message = "Price is required!")
    private int price;

    private String photoUrl;

    @NotNull(message = "Category is required!")
    private Category category;

    @NotNull(message = "Supplier is required!")
    private Supplier supplier;

}

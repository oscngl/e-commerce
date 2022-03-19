package com.osc.ecommerce.entities.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class CategoryDto {

    @NotEmpty(message = "Name is required!")
    @NotBlank(message = "Name is required!")
    private String name;

}

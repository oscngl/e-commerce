package com.osc.ecommerce.entities.dtos;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class AdminDto {

    @NotEmpty(message = "Name is required!")
    @NotBlank(message = "Name is required!")
    private String firstName;

    @NotEmpty(message = "Last Name is required!")
    @NotBlank(message = "Last Name is required!")
    private String lastName;

    @NotEmpty(message = "Email is required!")
    @NotBlank(message = "Email is required!")
    @Email(message = "Email is not valid!")
    private String email;

    @NotEmpty(message = "Password is required!")
    @NotBlank(message = "Password is required!")
    @Size(min = 8, max = 20, message = "Password must contain at least 8 and maximum 20 characters!")
    @Pattern(regexp = "^[0-9a-zA-Z]*$", message = "Password is not valid!")
    private String password;

}

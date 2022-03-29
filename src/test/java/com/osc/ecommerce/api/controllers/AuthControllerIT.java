package com.osc.ecommerce.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osc.ecommerce.entities.concretes.Admin;
import com.osc.ecommerce.entities.concretes.ConfirmationToken;
import com.osc.ecommerce.entities.dtos.AdminDto;
import com.osc.ecommerce.entities.dtos.CustomerDto;
import com.osc.ecommerce.entities.dtos.SupplierDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void itShouldRegisterAdminWhenRequestIsValid_isOk() throws Exception {

        AdminDto adminDto = new AdminDto(
                "firstName",
                "lastName",
                "email@gmail.com",
                "password"
        );

        mockMvc.perform(post("/api/auth/register/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(adminDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

    }

    @Test
    void itShouldNotRegisterAdminWhenRequestIsNotValid_isBadRequest() throws Exception {

        mockMvc.perform(post("/api/auth/register/admin")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void itShouldRegisterCustomerWhenRequestIsValid_isOk() throws Exception {

        CustomerDto customerDto = new CustomerDto(
                "firstName",
                "lastName",
                "email@gmail.com",
                "password"
        );

        mockMvc.perform(post("/api/auth/register/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

    }

    @Test
    void itShouldNotRegisterCustomerWhenRequestIsNotValid_isBadRequest() throws Exception {

        mockMvc.perform(post("/api/auth/register/customer")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void itShouldRegisterSupplierWhenRequestIsValid_isOk() throws Exception {

        SupplierDto supplierDto = new SupplierDto(
                "name",
                "email@gmail.com",
                "password"
        );

        mockMvc.perform(post("/api/auth/register/supplier")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplierDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

    }

    @Test
    void itShouldNotRegisterSupplierWhenRequestIsNotValid_isBadRequest() throws Exception {

        mockMvc.perform(post("/api/auth/register/supplier")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void itShouldConfirmTokenWhenRequestIsValid_isOk() throws Exception {

        ConfirmationToken confirmationToken = new ConfirmationToken(new Admin(1,"firstName","lastName"));
        String token = confirmationToken.getToken();

        mockMvc.perform(get("/api/auth/confirm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("token", objectMapper.writeValueAsString(token)))
                .andExpect(status().isOk());

    }

    @Test
    void itShouldNotConfirmTokenWhenRequestIsNotValid_isBadRequest() throws Exception {

        mockMvc.perform(get("/api/auth/confirm")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

}
package com.osc.ecommerce.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osc.ecommerce.dal.CustomerDao;
import com.osc.ecommerce.entities.concretes.Customer;
import com.osc.ecommerce.entities.dtos.CustomerDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomersControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerDao customerDao;

    @AfterEach
    void tearDown() {
        customerDao.deleteAll();
    }

    @Test
    void itShouldSaveWhenRequestIsValid_isOk() throws Exception {

        CustomerDto customerDto = new CustomerDto(
                "firstName",
                "lastName",
                "email@gmail.com",
                "password"
        );

        mockMvc.perform(post("/api/customers/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

    }

    @Test
    void itShouldNotSaveWhenRequestIsNotValid_isBadRequest() throws Exception {

        mockMvc.perform(post("/api/customers/save")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void itShouldGetByIdWhenExists_isOk() throws Exception {

        String email = "email@gmail.com";

        Customer customer = new Customer();
        customer.setFirstName("firstName");
        customer.setLastName("lastName");
        customer.setEmail(email);
        customer.setPassword("password");
        customer.setConfirmed(true);

        customerDao.save(customer);

        int id = customerDao.findByConfirmedIsTrueAndEmail(email).getId();

        mockMvc.perform(get("/api/customers/getById")
                        .param("id", String.valueOf(id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

    }

    @Test
    void itShouldNotGetByIdWhenDoesNotExists_isOk() throws Exception {

        mockMvc.perform(get("/api/customers/getById")
                        .param("id", String.valueOf(1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));

    }

    @Test
    void itShouldGetAll_isOk() throws Exception {

        mockMvc.perform(get("/api/customers/getAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

    }

    @Test
    void itShouldGetByEmailWhenExists_isOk() throws Exception {

        String email = "email@gmail.com";

        Customer customer = new Customer();
        customer.setFirstName("firstName");
        customer.setLastName("lastName");
        customer.setEmail(email);
        customer.setPassword("password");
        customer.setConfirmed(true);

        customerDao.save(customer);

        mockMvc.perform(get("/api/customers/getByEmail")
                        .param("email", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

    }

    @Test
    void itShouldNotGetByEmailWhenDoesNotExists_isOk() throws Exception {

        mockMvc.perform(get("/api/customers/getByEmail")
                        .param("email", "email@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));

    }

    @Test
    void itShouldNotGetByEmailWhenRequestIsNotValid_isOk() throws Exception {

        mockMvc.perform(get("/api/customers/getByEmail")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));

    }

}
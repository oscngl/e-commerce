package com.osc.ecommerce.api.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osc.ecommerce.dal.abstracts.CategoryDao;
import com.osc.ecommerce.entities.concretes.Category;
import com.osc.ecommerce.entities.dtos.CategoryDto;
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
class CategoriesControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryDao categoryDao;

    private final String token = "Bearer " + JWT.create().withArrayClaim("roles", new String[]{"ROLE_ADMIN"}).withIssuer("auth0").sign(Algorithm.HMAC256("secret"));

    @AfterEach
    void tearDown() {
        categoryDao.deleteAll();
    }

    @Test
    void itShouldSaveWhenRequestIsValid_isOk() throws Exception {

        CategoryDto categoryDto = new CategoryDto(
                "name"
        );

        mockMvc.perform(post("/api/v1/categories/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDto))
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

    }

    @Test
    void itShouldNotSaveWhenRequestIsNotValid_isBadRequest() throws Exception {

        mockMvc.perform(post("/api/v1/categories/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token))
                .andExpect(status().isBadRequest());

    }

    @Test
    void itShouldGetByIdWhenExists_isOk() throws Exception {

        String name = "name";

        Category category = new Category();
        category.setName(name);

        categoryDao.save(category);

        int id = categoryDao.findByName(name).getId();

        mockMvc.perform(get("/api/v1/categories/getById")
                        .param("id", String.valueOf(id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

    }

    @Test
    void itShouldNotGetByIdWhenDoesNotExists_isOk() throws Exception {

        mockMvc.perform(get("/api/v1/categories/getById")
                        .param("id", String.valueOf(1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));

    }

    @Test
    void itShouldGetAll_isOk() throws Exception {

        mockMvc.perform(get("/api/v1/categories/getAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

    }

    @Test
    void itShouldGetByNameWhenExists_isOk() throws Exception {

        String name = "name";

        Category category = new Category();
        category.setName(name);

        categoryDao.save(category);

        mockMvc.perform(get("/api/v1/categories/getByName")
                        .param("name", name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

    }

    @Test
    void itShouldNotGetByNameWhenDoesNotExists_isOk() throws Exception {

        mockMvc.perform(get("/api/v1/categories/getByName")
                        .param("name", "name")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));

    }

    @Test
    void itShouldNotGetByNameWhenRequestIsNotValid_isOk() throws Exception {

        mockMvc.perform(get("/api/v1/categories/getByName")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));

    }

}
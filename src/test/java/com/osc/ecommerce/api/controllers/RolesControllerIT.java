package com.osc.ecommerce.api.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osc.ecommerce.dal.abstracts.RoleDao;
import com.osc.ecommerce.entities.dtos.RoleDto;
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
class RolesControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoleDao roleDao;

    private final String token = "Bearer " + JWT.create().withArrayClaim("roles", new String[]{"ROLE_ADMIN"}).withIssuer("auth0").sign(Algorithm.HMAC256("secret"));

    @AfterEach
    void tearDown() {
        roleDao.deleteAll();
    }

    @Test
    void itShouldSaveWhenRequestIsValid_isOk() throws Exception {

        RoleDto roleDto = new RoleDto(
                "name"
        );

        mockMvc.perform(post("/api/roles/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roleDto))
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

    }

    @Test
    void itShouldNotSaveWhenRequestIsNotValid_isBadRequest() throws Exception {

        mockMvc.perform(post("/api/roles/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token))
                .andExpect(status().isBadRequest());

    }

}
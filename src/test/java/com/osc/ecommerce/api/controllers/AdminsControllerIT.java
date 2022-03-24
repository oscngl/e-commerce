package com.osc.ecommerce.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osc.ecommerce.dal.abstracts.AdminDao;
import com.osc.ecommerce.dal.abstracts.ConfirmationTokenDao;
import com.osc.ecommerce.dal.abstracts.UserDao;
import com.osc.ecommerce.entities.concretes.Admin;
import com.osc.ecommerce.entities.dtos.AdminDto;
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
class AdminsControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private ConfirmationTokenDao confirmationTokenDao;

    @AfterEach
    void tearDown() {
        confirmationTokenDao.deleteAll();
        adminDao.deleteAll();
        userDao.deleteAll();
    }

    @Test
    void itShouldSaveWhenRequestIsValid_isOk() throws Exception {

        AdminDto adminDto = new AdminDto(
                "firstName",
                "lastName",
                "email@gmail.com",
                "password"
        );

        mockMvc.perform(post("/api/admins/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(adminDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

    }

    @Test
    void itShouldNotSaveWhenRequestIsNotValid_isBadRequest() throws Exception {

        mockMvc.perform(post("/api/admins/save")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void itShouldGetByIdWhenExists_isOk() throws Exception {

        String email = "email@gmail.com";

        Admin admin = new Admin();
        admin.setFirstName("firstName");
        admin.setLastName("lastName");
        admin.setEmail(email);
        admin.setPassword("password");
        admin.setConfirmed(true);

        adminDao.save(admin);

        int id = adminDao.findByConfirmedIsTrueAndEmail(email).getId();

        mockMvc.perform(get("/api/admins/getById")
                        .param("id", String.valueOf(id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

    }

    @Test
    void itShouldNotGetByIdWhenDoesNotExists_isOk() throws Exception {

        mockMvc.perform(get("/api/admins/getById")
                        .param("id", String.valueOf(1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));

    }

    @Test
    void itShouldGetAll_isOk() throws Exception {

        mockMvc.perform(get("/api/admins/getAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

    }

    @Test
    void itShouldGetByEmailWhenExists_isOk() throws Exception {

        String email = "email@gmail.com";

        Admin admin = new Admin();
        admin.setFirstName("firstName");
        admin.setLastName("lastName");
        admin.setEmail(email);
        admin.setPassword("password");
        admin.setConfirmed(true);

        adminDao.save(admin);

        mockMvc.perform(get("/api/admins/getByEmail")
                        .param("email", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

    }

    @Test
    void itShouldNotGetByEmailWhenDoesNotExists_isOk() throws Exception {

        mockMvc.perform(get("/api/admins/getByEmail")
                        .param("email", "email@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));

    }

    @Test
    void itShouldNotGetByEmailWhenRequestIsNotValid_isOk() throws Exception {

        mockMvc.perform(get("/api/admins/getByEmail")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));

    }

}
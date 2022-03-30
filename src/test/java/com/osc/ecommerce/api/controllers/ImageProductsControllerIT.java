package com.osc.ecommerce.api.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osc.ecommerce.dal.abstracts.ImageProductDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.FileInputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ImageProductsControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ImageProductDao imageProductDao;

    private final String token = "Bearer " + JWT.create().withArrayClaim("roles", new String[]{"ROLE_SUPPLIER"}).withIssuer("auth0").sign(Algorithm.HMAC256("secret"));

    @AfterEach
    void tearDown() {
        imageProductDao.deleteAll();
    }

    @Test
    void itShouldSaveWhenRequestIsValid_isOk() throws Exception {

        int productId = 1;
        FileInputStream fileInputStream = new FileInputStream("/home/osc/Pictures/Wallpapers/wallpaper.jpeg");
        MockMultipartFile sampleImage = new MockMultipartFile(
                "image",
                "wallpaper.jpeg",
                "multipart/form-data",
                fileInputStream
        );

        mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/api/v1/images/products/upload")
                        .file(sampleImage)
                        .param("product_id", objectMapper.writeValueAsString(productId))
                        .header("Authorization", token))
                .andExpect(status().isOk());

    }

    @Test
    void itShouldNotSaveWhenRequestIsNotValid_isBadRequest() throws Exception {

        mockMvc.perform(post("/api/v1/images/products/upload")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .header("Authorization", token))
                .andExpect(status().isBadRequest());

    }

}
package com.osc.ecommerce.api.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osc.ecommerce.dal.abstracts.CategoryDao;
import com.osc.ecommerce.dal.abstracts.ProductDao;
import com.osc.ecommerce.dal.abstracts.SupplierDao;
import com.osc.ecommerce.dal.abstracts.UserDao;
import com.osc.ecommerce.entities.concretes.Category;
import com.osc.ecommerce.entities.concretes.Product;
import com.osc.ecommerce.entities.concretes.Supplier;
import com.osc.ecommerce.entities.dtos.ProductDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductsControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private SupplierDao supplierDao;

    private final String token = "Bearer " + JWT.create().withArrayClaim("roles", new String[]{"ROLE_SUPPLIER"}).withIssuer("auth0").sign(Algorithm.HMAC256("secret"));

    @AfterEach
    void tearDown() {
        productDao.deleteAll();
        categoryDao.deleteAll();
        supplierDao.deleteAll();
        userDao.deleteAll();
    }

    @Test
    void itShouldSaveWhenRequestIsValid_isOk() throws Exception {

        Category category = new Category();
        category.setName("name");
        categoryDao.save(category);

        Supplier supplier = new Supplier();
        supplier.setName("name");
        supplier.setEmail("email@gmail.com");
        supplier.setPassword("password");
        supplierDao.save(supplier);

        ProductDto productDto = new ProductDto(
                "name",
                "description",
                1,
                10,
                category,
                supplier
        );

        mockMvc.perform(post("/api/v1/products/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto))
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

    }

    @Test
    void itShouldNotSaveWhenRequestIsNotValid_isBadRequest() throws Exception {

        mockMvc.perform(post("/api/v1/products/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token))
                .andExpect(status().isBadRequest());

    }

    @Test
    @Transactional
    void itShouldUpdateWhenRequestIsValid_isOk() throws Exception {

        Category category = new Category();
        category.setName("name");
        categoryDao.save(category);

        Supplier supplier = new Supplier();
        supplier.setName("name");
        supplier.setEmail("email@gmail.com");
        supplier.setPassword("password");
        supplierDao.save(supplier);

        Product product = new Product();
        product.setName("name");
        product.setDescription("description");
        product.setPrice(1);
        product.setStockQuantity(1);
        product.setCategory(category);
        product.setSupplier(supplier);
        productDao.save(product);

        List<Product> list = productDao.findAll();
        product = list.get(0);

        product.setPrice(2);

        mockMvc.perform(put("/api/v1/products/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product))
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

    }

    @Test
    void itShouldNotUpdateWhenDoesNotExists_isOk() throws Exception {

        Category category = new Category();
        category.setName("name");
        categoryDao.save(category);

        Supplier supplier = new Supplier();
        supplier.setName("name");
        supplier.setEmail("email@gmail.com");
        supplier.setPassword("password");
        supplierDao.save(supplier);

        Product product = new Product();
        product.setName("name");
        product.setDescription("description");
        product.setPrice(1);
        product.setStockQuantity(1);
        product.setCategory(category);
        product.setSupplier(supplier);

        mockMvc.perform(put("/api/v1/products/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product))
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));

    }

    @Test
    void itShouldNotUpdateWhenRequestIsNotValid_isBadRequest() throws Exception {

        mockMvc.perform(put("/api/v1/products/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token))
                .andExpect(status().isBadRequest());

    }

    @Test
    void itShouldSetDisableWhenRequestIsValid_isOk() throws Exception {

        int id = 1;

        mockMvc.perform(put("/api/v1/products/setDisable")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", objectMapper.writeValueAsString(id))
                        .header("Authorization", token))
                .andExpect(status().isOk());

    }

    @Test
    void itShouldNotSetDisableWhenRequestIsNotValid_isBadRequest() throws Exception {

        mockMvc.perform(put("/api/v1/products/setDisable")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token))
                .andExpect(status().isBadRequest());

    }

    @Test
    void itShouldGetByIdWhenExists_isOk() throws Exception {

        Category category = new Category();
        category.setName("name");
        categoryDao.save(category);

        Supplier supplier = new Supplier();
        supplier.setName("name");
        supplier.setEmail("email@gmail.com");
        supplier.setPassword("password");
        supplierDao.save(supplier);

        Product product = new Product();
        product.setName("name");
        product.setDescription("description");
        product.setPrice(1);
        product.setStockQuantity(1);
        product.setCategory(category);
        product.setSupplier(supplier);

        productDao.save(product);

        List<Product> list = productDao.findAll();
        int id = list.get(0).getId();

        mockMvc.perform(get("/api/v1/products/getById")
                        .param("id", String.valueOf(id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

    }

    @Test
    void itShouldNotGetByIdWhenDoesNotExists_isOk() throws Exception {

        mockMvc.perform(get("/api/v1/products/getById")
                        .param("id", String.valueOf(1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));

    }

    @Test
    void itShouldGetAll_isOk() throws Exception {

        mockMvc.perform(get("/api/v1/products/getAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

    }

    @Test
    void itShouldGetAllByCategoryIdWhenExists_isOk() throws Exception {

        String name = "name";

        Category category = new Category();
        category.setName(name);
        categoryDao.save(category);

        int categoryId = categoryDao.findByName(name).getId();

        mockMvc.perform(get("/api/v1/products/getAllByCategoryId")
                        .param("categoryId", String.valueOf(categoryId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

    }

    @Test
    void itShouldGetAllBySupplierIdWhenExists_isOk() throws Exception {

        String email = "email@gmail.com";

        Supplier supplier = new Supplier();
        supplier.setName("name");
        supplier.setEmail(email);
        supplier.setPassword("password");
        supplier.setConfirmed(true);
        supplierDao.save(supplier);

        int supplierId = supplierDao.findByConfirmedIsTrueAndEmail(email).getId();

        mockMvc.perform(get("/api/v1/products/getAllBySupplierId")
                        .param("supplierId", String.valueOf(supplierId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

    }

}
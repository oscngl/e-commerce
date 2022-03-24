package com.osc.ecommerce.dal.abstracts;

import com.osc.ecommerce.entities.concretes.Category;
import com.osc.ecommerce.entities.concretes.Product;
import com.osc.ecommerce.entities.concretes.Supplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductDaoTest {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private SupplierDao supplierDao;

    @AfterEach
    void tearDown() {
        productDao.deleteAll();
        categoryDao.deleteAll();
        supplierDao.deleteAll();
    }

    @Test
    void itShouldCheckWhenCategoryIdExists() {

        // category created to give as a parameter to create Product
        Category category = new Category();
        category.setName("name");
        categoryDao.save(category);
        List<Category> categories = categoryDao.findAll();
        int categoryId = categories.get(0).getId();

        // supplier created to give as a parameter to create Product
        Supplier supplier = new Supplier();
        supplier.setName("name");
        supplier.setEmail("email");
        supplier.setPassword("password");
        supplierDao.save(supplier);

        Product product = new Product();
        product.setCategory(category);
        product.setName("name");
        product.setDescription("description");
        product.setPrice(1);
        product.setSupplier(supplier);
        productDao.save(product);

        List<Product> expected = productDao.findAllByCategory_Id(categoryId);

        assertThat(expected.isEmpty()).isFalse();
    }

    @Test
    void itShouldCheckWhenCategoryIdDoesNotExists() {

        List<Product> expected = productDao.findAllByCategory_Id(1234);

        assertThat(expected.isEmpty()).isTrue();
    }

    @Test
    void itShouldCheckWhenSupplierIdExists() {

        // category created to give as a parameter to create Product
        Category category = new Category();
        category.setName("name");
        categoryDao.save(category);

        // supplier created to give as a parameter to create Product
        Supplier supplier = new Supplier();
        supplier.setName("name");
        supplier.setEmail("email");
        supplier.setPassword("password");
        supplierDao.save(supplier);
        List<Supplier> suppliers = supplierDao.findAll();
        int supplierId = suppliers.get(0).getId();

        Product product = new Product();
        product.setCategory(category);
        product.setName("name");
        product.setDescription("description");
        product.setPrice(1);
        product.setSupplier(supplier);
        productDao.save(product);

        List<Product> expected = productDao.findAllBySupplier_Id(supplierId);

        assertThat(expected.isEmpty()).isFalse();
    }

    @Test
    void itShouldCheckWhenSupplierIdDoesNotExists() {

        List<Product> expected = productDao.findAllBySupplier_Id(1234);

        assertThat(expected.isEmpty()).isTrue();

    }

}
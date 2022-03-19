package com.osc.ecommerce.dal;

import com.osc.ecommerce.entities.concretes.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryDaoTest {

    @Autowired
    private CategoryDao categoryDao;

    @AfterEach
    void tearDown() {
        categoryDao.deleteAll();
    }

    @Test
    void itShouldCheckWhenNameExists() {

        Category category = new Category();
        category.setName("name");
        categoryDao.save(category);

        Category expected = categoryDao.findByName("name");

        assertThat(expected).isEqualTo(category);

    }

    @Test
    void itShouldCheckWhenNameDoesNotExists() {

        Category expected = categoryDao.findByName("name");

        assertThat(expected).isNull();

    }

}
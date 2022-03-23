package com.osc.ecommerce.business.concretes;

import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.dal.CategoryDao;
import com.osc.ecommerce.entities.concretes.Category;
import com.osc.ecommerce.entities.concretes.Product;
import com.osc.ecommerce.entities.dtos.CategoryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryManagerTest {

    private CategoryManager categoryManager;

    @Mock
    private CategoryDao categoryDao;

    @BeforeEach
    void setUp() {
        categoryManager = new CategoryManager(categoryDao, new ModelMapper());
    }

    @Test
    void canSave() {

        CategoryDto categoryDto = new CategoryDto(
                "name"
        );

        categoryManager.save(categoryDto);

        ArgumentCaptor<Category> categoryArgumentCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoryDao).save(categoryArgumentCaptor.capture());
        Category capturedCategory = categoryArgumentCaptor.getValue();
        assertThat(capturedCategory.getName()).isEqualTo(categoryDto.getName());

    }

    @Test
    void canGetById() {

        int id = 1;
        String name = "name";
        List<Product> productList = new ArrayList<>();
        Category category = new Category(id, name, productList);

        when(categoryDao.findById(id)).thenReturn(Optional.of(category));

        DataResult<Category> expected = categoryManager.getById(id);

        assertThat(expected.isSuccess()).isTrue();
        assertThat(expected.getData().getId()).isEqualTo(id);
        assertThat(expected.getData().getName()).isEqualTo(name);
        assertThat(expected.getData().getProducts()).isEqualTo(productList);

    }

    @Test
    void canGetAll() {

        categoryManager.getAll();

        verify(categoryDao).findAll();

    }

    @Test
    void canGetByEmail() {

        int id = 1;
        String name = "name";
        List<Product> productList = new ArrayList<>();
        Category category = new Category(id, name, productList);

        when(categoryDao.findByName(name)).thenReturn(category);

        DataResult<Category> expected = categoryManager.getByName(name);

        assertThat(expected.isSuccess()).isTrue();
        assertThat(expected.getData().getId()).isEqualTo(id);
        assertThat(expected.getData().getName()).isEqualTo(name);
        assertThat(expected.getData().getProducts()).isEqualTo(productList);

    }

}
package com.osc.ecommerce.business.concretes;

import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.dal.abstracts.CategoryDao;
import com.osc.ecommerce.entities.concretes.Category;
import com.osc.ecommerce.entities.dtos.CategoryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CategoryManagerTest {

    private CategoryManager categoryManager;

    @Mock
    private CategoryDao categoryDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryManager = new CategoryManager(
                categoryDao,
                new ModelMapper()
        );
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
        Category category = new Category();
        category.setId(id);

        given(categoryDao.findById(id)).willReturn(Optional.of(category));

        DataResult<Category> expected = categoryManager.getById(id);

        assertThat(expected.getData()).isEqualTo(category);

    }

    @Test
    void canGetAll() {

        categoryManager.getAll();

        verify(categoryDao).findAll();

    }

    @Test
    void canGetByEmail() {

        String name = "name";
        Category category = new Category();
        category.setName(name);

        given(categoryDao.findByName(name)).willReturn(category);

        DataResult<Category> expected = categoryManager.getByName(name);

        assertThat(expected.getData()).isEqualTo(category);

    }

}
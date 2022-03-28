package com.osc.ecommerce.business.concretes;

import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.core.utilities.results.Result;
import com.osc.ecommerce.dal.abstracts.ProductDao;
import com.osc.ecommerce.entities.concretes.Category;
import com.osc.ecommerce.entities.concretes.Product;
import com.osc.ecommerce.entities.concretes.Supplier;
import com.osc.ecommerce.entities.dtos.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductManagerTest {

    private ProductManager productManager;

    @Mock
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productManager = new ProductManager(
                productDao,
                new ModelMapper()
        );
    }

    @Test
    void canSave() {

        String name = "name";
        String description = "description";
        int price = 1;
        int stockQuantity = 1;
        ProductDto productDto = new ProductDto(
                name,
                description,
                price,
                stockQuantity,
                new Category(),
                new Supplier()
        );

        productManager.save(productDto);

        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productDao).save(productArgumentCaptor.capture());
        Product capturedProduct = productArgumentCaptor.getValue();

        assertThat(capturedProduct.getName()).isEqualTo(name);
        assertThat(capturedProduct.getDescription()).isEqualTo(description);
        assertThat(capturedProduct.getPrice()).isEqualTo(price);
        assertThat(capturedProduct.getStockQuantity()).isEqualTo(stockQuantity);

    }

    @Test
    void canUpdate() {

        int id = 1;
        String name = "name";
        String description = "description";
        int price = 1;
        int stockQuantity = 1;
        Category category = new Category();
        Supplier supplier = new Supplier();
        Product product = new Product(
                id,
                name,
                description,
                price,
                stockQuantity,
                true,
                category,
                supplier,
                new ArrayList<>()
        );

        String newName = "newName";
        Product newProduct = new Product(
                id,
                newName,
                description,
                price,
                stockQuantity,
                true,
                category,
                supplier,
                new ArrayList<>()
        );

        given(productDao.findById(id)).willReturn(Optional.of(product));

        productManager.update(newProduct);

        verify(productDao).save(newProduct);
        verify(productDao).findById(id);

    }

    @Test
    void canSetDisable() {

        int id = 1;

        given(productDao.findById(id)).willReturn(Optional.of(new Product()));

        Result expected = productManager.setDisable(id);

        assertThat(expected.isSuccess()).isTrue();

    }

    @Test
    void canGetById() {

        int id = 1;
        String name = "name";
        String description = "description";
        int price = 1;
        int stockQuantity = 1;
        Category category = new Category();
        Supplier supplier = new Supplier();
        Product product = new Product(
                id,
                name,
                description,
                price,
                stockQuantity,
                true,
                category,
                supplier,
                new ArrayList<>()
        );

        given(productDao.findById(id)).willReturn(Optional.of(product));

        DataResult<Product> expected = productManager.getById(id);

        assertThat(expected.getData()).isEqualTo(product);

    }

    @Test
    void canGetAll() {

        productManager.getAll();

        verify(productDao).findAllByEnabledIsTrue();

    }

    @Test
    void canGetAllByCategoryId() {

        int id = 1;

        productManager.getAllByCategoryId(id);

        verify(productDao).findAllByEnabledIsTrueAndCategory_Id(id);

    }

    @Test
    void getAllBySupplierId() {

        int id = 1;

        productManager.getAllBySupplierId(id);

        verify(productDao).findAllByEnabledIsTrueAndSupplier_Id(id);

    }

}
package com.osc.ecommerce.business.concretes;

import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.dal.ProductDao;
import com.osc.ecommerce.entities.concretes.Category;
import com.osc.ecommerce.entities.concretes.Product;
import com.osc.ecommerce.entities.concretes.Supplier;
import com.osc.ecommerce.entities.dtos.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductManagerTest {

    private ProductManager productManager;

    @Mock
    private ProductDao productDao;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        productManager = new ProductManager(productDao, modelMapper);
    }

    @Test
    void canSave() {

        ProductDto productDto = new ProductDto(
                "name",
                "description",
                1,
                "photoUrl",
                new Category(),
                new Supplier()
        );

        productManager.save(productDto);

        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productDao).save(productArgumentCaptor.capture());
        Product capturedProduct = productArgumentCaptor.getValue();
        ProductDto capturedProductDto = modelMapper.map(capturedProduct, ProductDto.class);
        assertThat(capturedProduct).isEqualTo(capturedProductDto);

    }

    @Test
    void canUpdate() {

        int id = 1;
        String name = "name";
        String description = "description";
        int price = 1;
        String photoUrl = "photoUrl";
        Category category = new Category();
        Supplier supplier = new Supplier();
        Product product = new Product(
                id,
                name,
                description,
                price,
                photoUrl,
                category,
                supplier
        );
        String newName = "newName";
        Product newProduct = new Product(
                id,
                newName,
                description,
                price,
                photoUrl,
                category,
                supplier
        );
        when(productDao.findById(id)).thenReturn(Optional.of(product));

        productManager.update(newProduct);

        verify(productDao).save(newProduct);
        verify(productDao).findById(id);

    }

    @Test
    void canGetById() {

        int id = 1;
        String name = "name";
        String description = "description";
        int price = 1;
        String photoUrl = "photoUrl";
        Category category = new Category();
        Supplier supplier = new Supplier();
        Product product = new Product(
                id,
                name,
                description,
                price,
                photoUrl,
                category,
                supplier
        );

        when(productDao.findById(id)).thenReturn(Optional.of(product));

        DataResult<Product> expected = productManager.getById(id);

        assertThat(expected.isSuccess()).isTrue();
        assertThat(expected.getData().getId()).isEqualTo(id);
        assertThat(expected.getData().getName()).isEqualTo(name);
        assertThat(expected.getData().getDescription()).isEqualTo(description);
        assertThat(expected.getData().getPrice()).isEqualTo(price);
        assertThat(expected.getData().getPhotoUrl()).isEqualTo(photoUrl);
        assertThat(expected.getData().getCategory()).isEqualTo(category);
        assertThat(expected.getData().getSupplier()).isEqualTo(supplier);

    }

    @Test
    void canGetAll() {

        productManager.getAll();

        verify(productDao).findAll();

    }

    @Test
    void canGetAllByCategoryId() {

        int id = 1;

        productManager.getAllByCategoryId(id);

        verify(productDao).findAllByCategory_Id(id);

    }

    @Test
    void getAllBySupplierId() {

        int id = 1;

        productManager.getAllBySupplierId(id);

        verify(productDao).findAllBySupplier_Id(id);

    }
}
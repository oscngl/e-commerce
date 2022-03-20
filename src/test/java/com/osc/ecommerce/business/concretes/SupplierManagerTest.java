package com.osc.ecommerce.business.concretes;

import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.dal.SupplierDao;
import com.osc.ecommerce.entities.concretes.Product;
import com.osc.ecommerce.entities.concretes.Supplier;
import com.osc.ecommerce.entities.dtos.SupplierDto;
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
class SupplierManagerTest {

    private SupplierManager supplierManager;

    @Mock
    private SupplierDao supplierDao;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        supplierManager = new SupplierManager(supplierDao, modelMapper);
    }

    @Test
    void canSave() {

        SupplierDto supplierDto = new SupplierDto(
                "name",
                "email@gmail.com",
                "12345678"
        );

        supplierManager.save(supplierDto);

        ArgumentCaptor<Supplier> supplierArgumentCaptor = ArgumentCaptor.forClass(Supplier.class);
        verify(supplierDao).save(supplierArgumentCaptor.capture());
        Supplier capturedSupplier = supplierArgumentCaptor.getValue();
        SupplierDto capturedSupplierDto = modelMapper.map(capturedSupplier, SupplierDto.class);
        assertThat(capturedSupplier).isEqualTo(capturedSupplierDto);

    }

    @Test
    void canGetById() {

        int id = 1;
        String name = "name";
        List<Product> productList = new ArrayList<>();
        Supplier supplier = new Supplier(id, name, productList);

        when(supplierDao.findById(id)).thenReturn(Optional.of(supplier));

        DataResult<Supplier> expected = supplierManager.getById(id);

        assertThat(expected.isSuccess()).isTrue();
        assertThat(expected.getData().getId()).isEqualTo(id);
        assertThat(expected.getData().getName()).isEqualTo(name);
        assertThat(expected.getData().getProducts()).isEqualTo(productList);

    }

    @Test
    void canGetAll() {

        supplierManager.getAll();

        verify(supplierDao).findAllByConfirmedIsTrue();

    }

    @Test
    void canGetByEmail() {

        int id = 1;
        String name = "name";
        String email = "email@gmail.com";
        List<Product> productList = new ArrayList<>();
        Supplier supplier = new Supplier(id, name, productList);
        supplier.setEmail(email);
        supplier.setConfirmed(true);

        when(supplierDao.findByConfirmedIsTrueAndEmail(email)).thenReturn(supplier);

        DataResult<Supplier> expected = supplierManager.getByEmail(email);

        assertThat(expected.isSuccess()).isTrue();
        assertThat(expected.getData().getId()).isEqualTo(id);
        assertThat(expected.getData().getName()).isEqualTo(name);
        assertThat(expected.getData().getEmail()).isEqualTo(email);
        assertThat(expected.getData().getProducts()).isEqualTo(productList);
        assertThat(expected.getData().isConfirmed()).isTrue();

    }

}
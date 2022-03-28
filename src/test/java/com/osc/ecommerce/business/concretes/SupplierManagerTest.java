package com.osc.ecommerce.business.concretes;

import com.osc.ecommerce.business.abstracts.ConfirmationTokenService;
import com.osc.ecommerce.business.abstracts.RoleService;
import com.osc.ecommerce.business.abstracts.UserService;
import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.core.utilities.results.ErrorDataResult;
import com.osc.ecommerce.core.utilities.results.SuccessDataResult;
import com.osc.ecommerce.dal.abstracts.SupplierDao;
import com.osc.ecommerce.entities.concretes.Role;
import com.osc.ecommerce.entities.concretes.Supplier;
import com.osc.ecommerce.entities.dtos.SupplierDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SupplierManagerTest {

    private SupplierManager supplierManager;

    @Mock
    private SupplierDao supplierDao;

    @Mock
    private UserService userService;

    @Mock
    private RoleService roleService;

    @Mock
    private ConfirmationTokenService confirmationTokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        supplierManager = new SupplierManager(
                supplierDao,
                userService,
                roleService,
                new ModelMapper(),
                new BCryptPasswordEncoder(),
                confirmationTokenService);
    }

    @Test
    void canSave() {

        SupplierDto supplierDto = new SupplierDto(
                "name",
                "email@gmail.com",
                "password"
        );

        given(userService.getByConfirmedEmail(supplierDto.getEmail())).willReturn(new ErrorDataResult<>());
        given(roleService.getByName("ROLE_SUPPLIER")).willReturn(new SuccessDataResult<>(new Role(), null));

        supplierManager.save(supplierDto);

        ArgumentCaptor<Supplier> supplierArgumentCaptor = ArgumentCaptor.forClass(Supplier.class);
        verify(supplierDao).save(supplierArgumentCaptor.capture());
        Supplier capturedSupplier = supplierArgumentCaptor.getValue();

        assertThat(capturedSupplier.getName()).isEqualTo(supplierDto.getName());
        assertThat(capturedSupplier.getEmail()).isEqualTo(supplierDto.getEmail());

    }

    @Test
    void canGetById() {

        int id = 1;
        Supplier supplier = new Supplier();
        supplier.setId(id);

        given(supplierDao.findById(id)).willReturn(Optional.of(supplier));

        DataResult<Supplier> expected = supplierManager.getById(id);

        assertThat(expected.getData()).isEqualTo(supplier);

    }

    @Test
    void canGetAll() {

        supplierManager.getAll();

        verify(supplierDao).findAllByConfirmedIsTrue();

    }

    @Test
    void canGetByEmail() {

        String email = "email@gmail.com";
        Supplier supplier = new Supplier();
        supplier.setId(1);
        supplier.setName("name");
        supplier.setEmail(email);
        supplier.setConfirmed(true);

        given(supplierDao.findByConfirmedIsTrueAndEmail(email)).willReturn(supplier);

        DataResult<Supplier> expected = supplierManager.getByEmail(email);

        assertThat(expected.getData()).isEqualTo(supplier);

    }

}
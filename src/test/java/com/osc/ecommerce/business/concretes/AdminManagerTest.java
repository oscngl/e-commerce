package com.osc.ecommerce.business.concretes;

import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.dal.AdminDao;
import com.osc.ecommerce.entities.concretes.Admin;
import com.osc.ecommerce.entities.dtos.AdminDto;
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
class AdminManagerTest {

    private AdminManager adminManager;

    @Mock
    private AdminDao adminDao;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        adminManager = new AdminManager(adminDao, modelMapper);
    }

    @Test
    void canSave() {

        AdminDto adminDto = new AdminDto(
                "firstName",
                "lastName",
                "email@gmail.com",
                "12345678"
        );

        adminManager.save(adminDto);

        ArgumentCaptor<Admin> adminArgumentCaptor = ArgumentCaptor.forClass(Admin.class);
        verify(adminDao).save(adminArgumentCaptor.capture());
        Admin capturedAdmin = adminArgumentCaptor.getValue();
        AdminDto capturedAdminDto = modelMapper.map(capturedAdmin, AdminDto.class);
        assertThat(capturedAdmin).isEqualTo(capturedAdminDto);

    }

    @Test
    void canGetById() {

        int id = 1;
        String firstName = "firstName";
        String lastName = "lastName";
        Admin admin = new Admin(id, firstName, lastName);

        when(adminDao.findById(id)).thenReturn(Optional.of(admin));

        DataResult<Admin> expected = adminManager.getById(id);

        assertThat(expected.isSuccess()).isTrue();
        assertThat(expected.getData().getId()).isEqualTo(id);
        assertThat(expected.getData().getFirstName()).isEqualTo(firstName);
        assertThat(expected.getData().getLastName()).isEqualTo(lastName);

    }

    @Test
    void canGetAll() {

        adminManager.getAll();

        verify(adminDao).findAllByConfirmedIsTrue();

    }

    @Test
    void canGetByEmail() {

        int id = 1;
        String firstName = "firstName";
        String lastName = "lastName";
        String email = "email@gmail.com";
        Admin admin = new Admin(id, firstName, lastName);
        admin.setEmail(email);
        admin.setConfirmed(true);

        when(adminDao.findByConfirmedIsTrueAndEmail(email)).thenReturn(admin);

        DataResult<Admin> expected = adminManager.getByEmail(email);

        assertThat(expected.isSuccess()).isTrue();
        assertThat(expected.getData().getId()).isEqualTo(id);
        assertThat(expected.getData().getFirstName()).isEqualTo(firstName);
        assertThat(expected.getData().getLastName()).isEqualTo(lastName);
        assertThat(expected.getData().getEmail()).isEqualTo(email);
        assertThat(expected.getData().isConfirmed()).isTrue();

    }

}
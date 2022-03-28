package com.osc.ecommerce.business.concretes;

import com.osc.ecommerce.business.abstracts.ConfirmationTokenService;
import com.osc.ecommerce.business.abstracts.RoleService;
import com.osc.ecommerce.business.abstracts.UserService;
import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.core.utilities.results.ErrorDataResult;
import com.osc.ecommerce.core.utilities.results.SuccessDataResult;
import com.osc.ecommerce.dal.abstracts.AdminDao;
import com.osc.ecommerce.entities.concretes.Admin;
import com.osc.ecommerce.entities.concretes.Role;
import com.osc.ecommerce.entities.dtos.AdminDto;
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
class AdminManagerTest {

    private AdminManager adminManager;

    @Mock
    private AdminDao adminDao;

    @Mock
    private UserService userService;

    @Mock
    private RoleService roleService;

    @Mock
    private ConfirmationTokenService confirmationTokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adminManager = new AdminManager(
                adminDao,
                userService,
                roleService,
                new ModelMapper(),
                new BCryptPasswordEncoder(),
                confirmationTokenService);
    }

    @Test
    void canSave() {

        String firstName = "firstName";
        String lastName = "lastName";
        String email = "email@gmail.com";
        AdminDto adminDto = new AdminDto(
                firstName,
                lastName,
                email,
                "password"
        );

        given(userService.getByConfirmedEmail(adminDto.getEmail())).willReturn(new ErrorDataResult<>());
        given(roleService.getByName("ROLE_ADMIN")).willReturn(new SuccessDataResult<>(new Role(), null));

        adminManager.save(adminDto);

        ArgumentCaptor<Admin> adminArgumentCaptor = ArgumentCaptor.forClass(Admin.class);
        verify(adminDao).save(adminArgumentCaptor.capture());
        Admin capturedAdmin = adminArgumentCaptor.getValue();

        assertThat(capturedAdmin.getFirstName()).isEqualTo(firstName);
        assertThat(capturedAdmin.getLastName()).isEqualTo(lastName);
        assertThat(capturedAdmin.getEmail()).isEqualTo(email);

    }

    @Test
    void canGetById() {

        int id = 1;
        Admin admin = new Admin();
        admin.setId(id);

        given(adminDao.findById(id)).willReturn(Optional.of(admin));

        DataResult<Admin> expected = adminManager.getById(id);

        assertThat(expected.getData()).isEqualTo(admin);

    }

    @Test
    void canGetAll() {

        adminManager.getAll();

        verify(adminDao).findAllByConfirmedIsTrue();

    }

    @Test
    void canGetByEmail() {

        String email = "email@gmail.com";
        Admin admin = new Admin();
        admin.setId(1);
        admin.setFirstName("firstName");
        admin.setLastName("lastName");
        admin.setEmail(email);
        admin.setConfirmed(true);

        given(adminDao.findByConfirmedIsTrueAndEmail(email)).willReturn(admin);

        DataResult<Admin> expected = adminManager.getByEmail(email);

        assertThat(expected.getData()).isEqualTo(admin);

    }

}
package com.osc.ecommerce.business.concretes;

import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.dal.abstracts.UserDao;
import com.osc.ecommerce.entities.abstracts.User;
import com.osc.ecommerce.entities.concretes.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserManagerTest {

    private UserManager userManager;

    @Mock
    private UserDao userDao;

    @BeforeEach
    void setUp() {
        userManager = new UserManager(userDao);
    }

    @Test
    void canConfirm() {

        int id = 1;
        String email = "email@gmail.com";
        Admin admin = new Admin();
        admin.setId(1);
        admin.setFirstName("firstName");
        admin.setLastName("lastName");
        admin.setEmail(email);
        admin.setPassword("password");

        when(userDao.findById(id)).thenReturn(Optional.of(admin));

        userManager.confirm(id);

        User expected = userDao.findById(id).orElse(null);

        assertThat(expected.isConfirmed()).isTrue();



    }

    @Test
    void canGetByConfirmedEmail() {

        int id = 1;
        String email = "email@gmail.com";
        String password = "password";
        Admin admin = new Admin();
        admin.setId(id);
        admin.setFirstName("firstName");
        admin.setLastName("lastName");
        admin.setEmail(email);
        admin.setPassword(password);
        admin.setConfirmed(true);

        when(userDao.findByConfirmedIsTrueAndEmail(email)).thenReturn(admin);

        DataResult<User> expected = userManager.getByConfirmedEmail(email);

        assertThat(expected.isSuccess()).isTrue();
        assertThat(expected.getData().getId()).isEqualTo(id);
        assertThat(expected.getData().getEmail()).isEqualTo(email);
        assertThat(expected.getData().getPassword()).isEqualTo(password);
        assertThat(expected.getData().isConfirmed()).isTrue();

    }

    @Test
    void canGetByEmail() {

        int id = 1;
        String email = "email@gmail.com";
        String password = "password";
        Admin admin = new Admin();
        admin.setId(id);
        admin.setFirstName("firstName");
        admin.setLastName("lastName");
        admin.setEmail(email);
        admin.setPassword(password);

        when(userDao.findByEmail(email)).thenReturn(admin);

        DataResult<User> expected = userManager.getByEmail(email);

        assertThat(expected.isSuccess()).isTrue();
        assertThat(expected.getData().getId()).isEqualTo(id);
        assertThat(expected.getData().getEmail()).isEqualTo(email);
        assertThat(expected.getData().getPassword()).isEqualTo(password);

    }

}
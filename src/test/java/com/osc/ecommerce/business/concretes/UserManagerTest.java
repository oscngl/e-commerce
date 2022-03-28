package com.osc.ecommerce.business.concretes;

import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.dal.abstracts.UserDao;
import com.osc.ecommerce.entities.abstracts.User;
import com.osc.ecommerce.entities.concretes.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserManagerTest {

    private UserManager userManager;

    @Mock
    private UserDao userDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userManager = new UserManager(
                userDao
        );
    }

    @Test
    void canConfirm() {

        int id = 1;
        Admin admin = new Admin();
        admin.setId(id);

        given(userDao.findById(id)).willReturn(Optional.of(admin));

        userManager.confirm(id);

        User expected = userDao.findById(id).orElse(null);

        assertThat(expected != null && expected.isConfirmed()).isTrue();

    }

    @Test
    void canGetByConfirmedEmail() {

        String email = "email@gmail.com";
        Admin admin = new Admin();
        admin.setId(1);
        admin.setFirstName("firstName");
        admin.setLastName("lastName");
        admin.setEmail(email);
        admin.setPassword("password");
        admin.setConfirmed(true);

        given(userDao.findByConfirmedIsTrueAndEmail(email)).willReturn(admin);

        DataResult<User> expected = userManager.getByConfirmedEmail(email);

        assertThat(expected.getData()).isEqualTo(admin);

    }

    @Test
    void canGetByEmail() {

        String email = "email@gmail.com";
        Admin admin = new Admin();
        admin.setId(1);
        admin.setFirstName("firstName");
        admin.setLastName("lastName");
        admin.setEmail(email);
        admin.setPassword("password");

        given(userDao.findByEmail(email)).willReturn(admin);

        DataResult<User> expected = userManager.getByEmail(email);

        assertThat(expected.getData()).isEqualTo(admin);

    }

}
package com.osc.ecommerce.dal.abstracts;

import com.osc.ecommerce.entities.abstracts.User;
import com.osc.ecommerce.entities.concretes.Admin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AdminDao adminDao;

    @AfterEach
    void tearDown() {
        userDao.deleteAll();
    }

    @Test
    void itShouldCheckWhenEmailExists() {

        String email = "email@gmail.com";
        String password = "password";
        Admin admin = new Admin();
        admin.setFirstName("firstName");
        admin.setLastName("lastName");
        admin.setEmail(email);
        admin.setPassword("password");
        adminDao.save(admin);

        User expected = userDao.findByEmail(email);

        assertThat(expected.getEmail()).isEqualTo(email);
        assertThat(expected.getPassword()).isEqualTo(password);

    }

    @Test
    void itShouldCheckWhenEmailDoesNotExists() {

        User expected = userDao.findByEmail("email@gmail.com");

        assertThat(expected).isNull();

    }

    @Test
    void itShouldCheckWhenConfirmedIsTrueAndEmailExists() {

        String email = "email@gmail.com";
        String password = "password";
        Admin admin = new Admin();
        admin.setFirstName("firstName");
        admin.setLastName("lastName");
        admin.setEmail(email);
        admin.setPassword(password);
        admin.setConfirmed(true);
        adminDao.save(admin);

        User expected = userDao.findByConfirmedIsTrueAndEmail(email);

        assertThat(expected.getEmail()).isEqualTo(email);
        assertThat(expected.getPassword()).isEqualTo(password);

    }

    @Test
    void itShouldCheckWhenConfirmedIsNotTrueAndEmailExists() {

        String email = "email@gmail.com";
        Admin admin = new Admin();
        admin.setEmail(email);
        admin.setPassword("password");
        admin.setFirstName("firstName");
        admin.setLastName("lastName");
        adminDao.save(admin);

        User expected = userDao.findByConfirmedIsTrueAndEmail(email);

        assertThat(expected).isNull();

    }

    @Test
    void itShouldCheckWhenUserDoesNotExists() {

        String email = "email@gmail.com";

        User expected = userDao.findByConfirmedIsTrueAndEmail(email);

        assertThat(expected).isNull();

    }

    @Test
    void itShouldCheckWhenConfirmedExists() {

        Admin admin = new Admin();
        admin.setFirstName("firstName");
        admin.setLastName("lastName");
        admin.setEmail("email@gmail.com");
        admin.setPassword("password");
        admin.setConfirmed(true);
        adminDao.save(admin);

        List<User> expected = userDao.findAllByConfirmedIsTrue();

        assertThat(expected.isEmpty()).isFalse();

    }

    @Test
    void itShouldCheckWhenConfirmedDoesNotExists() {

        List<User> expected = userDao.findAllByConfirmedIsTrue();

        assertThat(expected.isEmpty()).isTrue();

    }

}
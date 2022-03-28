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
        adminDao.deleteAll();
        userDao.deleteAll();
    }

    @Test
    void itShouldFindByEmailWhenUserWithEmailExists() {

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
    void itShouldNotFindByEmailWhenUserWithEmailDoesNotExists() {

        String email = "email@gmail.com";

        User expected = userDao.findByEmail(email);

        assertThat(expected).isNull();

    }

    @Test
    void itShouldFindByConfirmedIsTrueAndEmailWhenConfirmedIsTrueAndUserWithEmailExists() {

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
    void itShouldNotFindByConfirmedIsTrueAndEmailWhenConfirmedIsFalseAndUserWithEmailExists() {

        String email = "email@gmail.com";
        String password = "password";
        Admin admin = new Admin();
        admin.setFirstName("firstName");
        admin.setLastName("lastName");
        admin.setEmail(email);
        admin.setPassword(password);
        admin.setConfirmed(false);
        adminDao.save(admin);

        User expected = userDao.findByConfirmedIsTrueAndEmail(email);

        assertThat(expected).isNull();

    }

    @Test
    void itShouldNotFindByConfirmedIsTrueAndEmailWhenUserWithEmailDoesNotExists() {

        String email = "email@gmail.com";

        User expected = userDao.findByConfirmedIsTrueAndEmail(email);

        assertThat(expected).isNull();

    }

    @Test
    void itShouldFindAllByConfirmedIsTrueWhenConfirmedIsTrueAndUserExists() {

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
    void itShouldNotFindAllByConfirmedIsTrueWhenConfirmedIsFalseAndUserExists() {

        Admin admin = new Admin();
        admin.setFirstName("firstName");
        admin.setLastName("lastName");
        admin.setEmail("email@gmail.com");
        admin.setPassword("password");
        admin.setConfirmed(false);
        adminDao.save(admin);

        List<User> expected = userDao.findAllByConfirmedIsTrue();

        assertThat(expected.isEmpty()).isTrue();

    }

    @Test
    void itShouldNotFindAllByConfirmedIsTrueWhenUserDoesNotExists() {

        List<User> expected = userDao.findAllByConfirmedIsTrue();

        assertThat(expected.isEmpty()).isTrue();

    }

}
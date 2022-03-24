package com.osc.ecommerce.dal;

import com.osc.ecommerce.dal.abstracts.AdminDao;
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
class AdminDaoTest {

    @Autowired
    private AdminDao adminDao;

    @AfterEach
    void tearDown() {
        adminDao.deleteAll();
    }

    @Test
    void itShouldCheckWhenConfirmedIsTrueAndEmailExists() {

        String email = "oscngl@gmail.com";
        Admin admin = new Admin();
        admin.setConfirmed(true);
        admin.setEmail(email);
        admin.setPassword("password");
        admin.setFirstName("firstName");
        admin.setLastName("lastName");
        adminDao.save(admin);

        Admin expected = adminDao.findByConfirmedIsTrueAndEmail(email);

        assertThat(expected).isEqualTo(admin);

    }

    @Test
    void itShouldCheckWhenConfirmedIsNotTrueAndEmailExists() {

        String email = "oscngl@gmail.com";
        Admin admin = new Admin();
        admin.setEmail(email);
        admin.setPassword("password");
        admin.setFirstName("firstName");
        admin.setLastName("lastName");
        adminDao.save(admin);

        Admin expected = adminDao.findByConfirmedIsTrueAndEmail(email);

        assertThat(expected).isNull();

    }

    @Test
    void itShouldCheckWhenEmailDoesNotExists() {

        String email = "oscngl@gmail.com";

        Admin expected = adminDao.findByConfirmedIsTrueAndEmail(email);

        assertThat(expected).isNull();

    }

    @Test
    void itShouldCheckWhenConfirmedExists() {

        Admin admin = new Admin();
        admin.setConfirmed(true);
        admin.setEmail("email");
        admin.setPassword("password");
        admin.setFirstName("firstName");
        admin.setLastName("lastName");
        adminDao.save(admin);

        List<Admin> expected = adminDao.findAllByConfirmedIsTrue();

        assertThat(expected.isEmpty()).isFalse();

    }

    @Test
    void itShouldCheckWhenConfirmedDoesNotExists() {

        List<Admin> expected = adminDao.findAllByConfirmedIsTrue();

        assertThat(expected.isEmpty()).isTrue();

    }

}
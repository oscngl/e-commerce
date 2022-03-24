package com.osc.ecommerce.dal.abstracts;

import com.osc.ecommerce.entities.concretes.Admin;
import com.osc.ecommerce.entities.concretes.ConfirmationToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ConfirmationTokenDaoTest {

    @Autowired
    private ConfirmationTokenDao confirmationTokenDao;

    @Autowired
    private AdminDao adminDao;

    @AfterEach
    void tearDown() {
        confirmationTokenDao.deleteAll();
        adminDao.deleteAll();
    }

    @Test
    void itShouldCheckWhenTokenExists() {

        // user created to give as a parameter to create ConfirmationToken
        String email = "oscngl@gmail.com";
        Admin admin = new Admin();
        admin.setConfirmed(true);
        admin.setEmail(email);
        admin.setPassword("password");
        admin.setFirstName("firstName");
        admin.setLastName("lastName");
        adminDao.save(admin);
        admin = adminDao.findByConfirmedIsTrueAndEmail(email);

        ConfirmationToken confirmationToken = new ConfirmationToken(admin);
        confirmationTokenDao.save(confirmationToken);
        String token = confirmationToken.getToken();

        ConfirmationToken expected = confirmationTokenDao.findByToken(token);

        assertThat(expected).isEqualTo(confirmationToken);

    }

    @Test
    void itShouldCheckWhenTokenDoesNotExists() {

        String token = "1234";

        ConfirmationToken expected = confirmationTokenDao.findByToken(token);

        assertThat(expected).isNull();

    }

}
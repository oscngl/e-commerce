package com.osc.ecommerce.dal;

import com.osc.ecommerce.entities.concretes.Customer;
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
class CustomerDaoTest {

    @Autowired
    private CustomerDao customerDao;

    @AfterEach
    void tearDown() {
        customerDao.deleteAll();
    }

    @Test
    void itShouldCheckWhenConfirmedIsTrueAndEmailExists() {

        String email = "oscngl@gmail.com";
        Customer customer = new Customer();
        customer.setConfirmed(true);
        customer.setEmail(email);
        customer.setPassword("password");
        customer.setFirstName("firstName");
        customer.setLastName("lastName");
        customerDao.save(customer);

        Customer expected = customerDao.findByConfirmedIsTrueAndEmail(email);

        assertThat(expected).isEqualTo(customer);
    }

    @Test
    void itShouldCheckWhenConfirmedIsNotTrueAndEmailExists() {

        String email = "oscngl@gmail.com";
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword("password");
        customer.setFirstName("firstName");
        customer.setLastName("lastName");
        customerDao.save(customer);

        Customer expected = customerDao.findByConfirmedIsTrueAndEmail(email);

        assertThat(expected).isNull();
    }

    @Test
    void itShouldCheckWhenEmailDoesNotExists() {

        String email = "oscngl@gmail.com";

        Customer expected = customerDao.findByConfirmedIsTrueAndEmail(email);

        assertThat(expected).isNull();

    }

    @Test
    void itShouldCheckWhenConfirmedExists() {

        Customer customer = new Customer();
        customer.setConfirmed(true);
        customer.setEmail("email");
        customer.setPassword("password");
        customer.setFirstName("firstName");
        customer.setLastName("lastName");
        customerDao.save(customer);

        List<Customer> expected = customerDao.findAllByConfirmedIsTrue();

        assertThat(expected.isEmpty()).isFalse();

    }

    @Test
    void itShouldCheckWhenConfirmedDoesNotExists() {

        List<Customer> expected = customerDao.findAllByConfirmedIsTrue();

        assertThat(expected.isEmpty()).isTrue();

    }

}
package com.osc.ecommerce.dal;

import com.osc.ecommerce.entities.concretes.Supplier;
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
class SupplierDaoTest {

    @Autowired
    private SupplierDao supplierDao;

    @AfterEach
    void tearDown() {
        supplierDao.deleteAll();
    }

    @Test
    void itShouldCheckWhenConfirmedIsTrueAndEmailExists() {

        String email = "oscngl@gmail.com";
        Supplier supplier = new Supplier();
        supplier.setConfirmed(true);
        supplier.setEmail(email);
        supplier.setPassword("password");
        supplier.setName("name");
        supplierDao.save(supplier);

        Supplier expected = supplierDao.findByConfirmedIsTrueAndEmail(email);

        assertThat(expected).isEqualTo(supplier);

    }

    @Test
    void itShouldCheckWhenConfirmedIsNotTrueAndEmailExists() {

        String email = "oscngl@gmail.com";
        Supplier supplier = new Supplier();
        supplier.setEmail(email);
        supplier.setPassword("password");
        supplier.setName("name");
        supplierDao.save(supplier);

        Supplier expected = supplierDao.findByConfirmedIsTrueAndEmail(email);

        assertThat(expected).isNull();

    }

    @Test
    void itShouldCheckWhenEmailDoesNotExists() {

        String email = "oscngl@gmail.com";

        Supplier expected = supplierDao.findByConfirmedIsTrueAndEmail(email);

        assertThat(expected).isNull();

    }

    @Test
    void itShouldCheckWhenConfirmedExists() {

        Supplier supplier = new Supplier();
        supplier.setConfirmed(true);
        supplier.setEmail("email");
        supplier.setPassword("password");
        supplier.setName("name");
        supplierDao.save(supplier);

        List<Supplier> expected = supplierDao.findAllByConfirmedIsTrue();

        assertThat(expected.isEmpty()).isFalse();

    }

    @Test
    void itShouldCheckWhenConfirmedDoesNotExists() {

        List<Supplier> expected = supplierDao.findAllByConfirmedIsTrue();

        assertThat(expected.isEmpty()).isTrue();

    }

}
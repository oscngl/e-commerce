package com.osc.ecommerce.dal.abstracts;

import com.osc.ecommerce.entities.concretes.Role;
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
class RoleDaoTest {

    @Autowired
    private RoleDao roleDao;

    @AfterEach
    void tearDown() {
        roleDao.deleteAll();
    }

    @Test
    void itShouldFindByNameWhenNameExists() {

        String name = "name";
        Role role = new Role();
        role.setName(name);
        roleDao.save(role);

        Role expected = roleDao.findByName(name);

        assertThat(expected).isEqualTo(role);

    }

    @Test
    void itShouldNotFindByNameWhenNameDoesNotExists() {

        String name = "name";

        Role expected = roleDao.findByName(name);

        assertThat(expected).isNull();

    }

}
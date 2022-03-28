package com.osc.ecommerce.business.concretes;

import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.dal.abstracts.RoleDao;
import com.osc.ecommerce.entities.concretes.Role;
import com.osc.ecommerce.entities.dtos.RoleDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RoleManagerTest {

    private RoleManager roleManager;

    @Mock
    private RoleDao roleDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        roleManager = new RoleManager(
                roleDao,
                new ModelMapper()
        );
    }

    @Test
    void canSave() {

        String name = "name";
        RoleDto roleDto = new RoleDto(
                name
        );

        roleManager.save(roleDto);

        ArgumentCaptor<Role> roleArgumentCaptor = ArgumentCaptor.forClass(Role.class);
        verify(roleDao).save(roleArgumentCaptor.capture());
        Role capturedRole = roleArgumentCaptor.getValue();

        assertThat(capturedRole.getName()).isEqualTo(name);

    }

    @Test
    void canGetByName() {

        String name = "name";
        Role role = new Role();
        role.setId(1);
        role.setName(name);

        given(roleDao.findByName(name)).willReturn(role);

        DataResult<Role> expected = roleManager.getByName(name);

        assertThat(expected.getData()).isEqualTo(role);

    }

}
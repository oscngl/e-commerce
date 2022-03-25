package com.osc.ecommerce.business.concretes;

import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.core.utilities.results.Result;
import com.osc.ecommerce.dal.abstracts.RoleDao;
import com.osc.ecommerce.entities.concretes.Role;
import com.osc.ecommerce.entities.dtos.RoleDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleManagerTest {

    private RoleManager roleManager;

    @Mock
    private RoleDao roleDao;

    @BeforeEach
    void setUp() {
        roleManager = new RoleManager(roleDao, new ModelMapper());
    }

    @Test
    void canSave() {

        RoleDto roleDto = new RoleDto(
                "name"
        );

        Result result = roleManager.save(roleDto);

        ArgumentCaptor<Role> roleArgumentCaptor = ArgumentCaptor.forClass(Role.class);
        verify(roleDao).save(roleArgumentCaptor.capture());
        Role capturedRole = roleArgumentCaptor.getValue();
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getMessage()).isEqualTo("Role saved.");
        assertThat(capturedRole.getName()).isEqualTo(roleDto.getName());

    }

    @Test
    void canGetByName() {

        int id = 1;
        String name = "name";
        Role role = new Role();
        role.setId(id);
        role.setName(name);

        when(roleDao.findByName(name)).thenReturn(role);

        DataResult<Role> expected = roleManager.getByName(name);

        assertThat(expected.isSuccess()).isTrue();
        assertThat(expected.getData().getId()).isEqualTo(id);
        assertThat(expected.getData().getName()).isEqualTo(name);

    }

}
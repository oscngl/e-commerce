package com.osc.ecommerce.business.concretes;

import com.osc.ecommerce.business.abstracts.ConfirmationTokenService;
import com.osc.ecommerce.business.abstracts.RoleService;
import com.osc.ecommerce.business.abstracts.UserService;
import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.core.utilities.results.ErrorDataResult;
import com.osc.ecommerce.core.utilities.results.SuccessDataResult;
import com.osc.ecommerce.dal.abstracts.CustomerDao;
import com.osc.ecommerce.entities.concretes.Customer;
import com.osc.ecommerce.entities.concretes.Role;
import com.osc.ecommerce.entities.dtos.CustomerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerManagerTest {

    private CustomerManager customerManager;

    @Mock
    private CustomerDao customerDao;

    @Mock
    private UserService userService;

    @Mock
    private RoleService roleService;

    @Mock
    private ConfirmationTokenService confirmationTokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerManager = new CustomerManager(
                customerDao,
                userService,
                roleService,
                new ModelMapper(),
                new BCryptPasswordEncoder(),
                confirmationTokenService);
    }

    @Test
    void canSave() {

        String firstName = "firstName";
        String lastName = "lastName";
        String email = "email@gmail.com";
        CustomerDto customerDto = new CustomerDto(
                firstName,
                lastName,
                email,
                "12345678"
        );
        given(userService.getByConfirmedEmail(customerDto.getEmail())).willReturn(new ErrorDataResult<>());

        given(roleService.getByName("ROLE_CUSTOMER")).willReturn(new SuccessDataResult<>(new Role(), null));

        customerManager.save(customerDto);

        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).save(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getFirstName()).isEqualTo(customerDto.getFirstName());
        assertThat(capturedCustomer.getLastName()).isEqualTo(customerDto.getLastName());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customerDto.getEmail());

    }

    @Test
    void canGetById() {

        int id = 1;
        Customer customer = new Customer();
        customer.setId(id);
        customer.setFirstName("firstName");
        customer.setLastName("lastName");

        given(customerDao.findById(id)).willReturn(Optional.of(customer));

        DataResult<Customer> expected = customerManager.getById(id);

        assertThat(expected.getData()).isEqualTo(customer);

    }

    @Test
    void canGetAll() {

        customerManager.getAll();

        verify(customerDao).findAllByConfirmedIsTrue();

    }

    @Test
    void canGetByEmail() {

        String email = "email@gmail.com";
        Customer customer = new Customer();
        customer.setId(1);
        customer.setFirstName("firstName");
        customer.setLastName("lastName");
        customer.setEmail(email);
        customer.setConfirmed(true);

        given(customerDao.findByConfirmedIsTrueAndEmail(email)).willReturn(customer);

        DataResult<Customer> expected = customerManager.getByEmail(email);

        assertThat(expected.getData()).isEqualTo(customer);

    }

}
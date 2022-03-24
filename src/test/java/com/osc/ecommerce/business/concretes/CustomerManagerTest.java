package com.osc.ecommerce.business.concretes;

import com.osc.ecommerce.business.abstracts.ConfirmationTokenService;
import com.osc.ecommerce.business.abstracts.UserService;
import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.dal.abstracts.CustomerDao;
import com.osc.ecommerce.entities.concretes.Customer;
import com.osc.ecommerce.entities.dtos.CustomerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerManagerTest {

    private CustomerManager customerManager;

    @Mock
    private CustomerDao customerDao;

    @Mock
    private UserService userService;

    @Mock
    private ConfirmationTokenService confirmationTokenService;

    @BeforeEach
    void setUp() {
        customerManager = new CustomerManager(customerDao, userService, new ModelMapper(), new BCryptPasswordEncoder(), confirmationTokenService);
    }

    @Test
    void canSave() {

        CustomerDto customerDto = new CustomerDto(
                "firstName",
                "lastName",
                "email@gmail.com",
                "12345678"
        );

        DataResult<String> result = customerManager.save(customerDto);

        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).save(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getMessage()).isEqualTo("Customer saved.");
        assertThat(capturedCustomer.getFirstName()).isEqualTo(customerDto.getFirstName());
        assertThat(capturedCustomer.getLastName()).isEqualTo(customerDto.getLastName());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customerDto.getEmail());

    }

    @Test
    void canGetById() {

        int id = 1;
        String firstName = "firstName";
        String lastName = "lastName";
        Customer customer = new Customer();
        customer.setId(id);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);

        when(customerDao.findById(id)).thenReturn(Optional.of(customer));

        DataResult<Customer> expected = customerManager.getById(id);

        assertThat(expected.isSuccess()).isTrue();
        assertThat(expected.getData().getId()).isEqualTo(id);
        assertThat(expected.getData().getFirstName()).isEqualTo(firstName);
        assertThat(expected.getData().getLastName()).isEqualTo(lastName);

    }

    @Test
    void canGetAll() {

        customerManager.getAll();

        verify(customerDao).findAllByConfirmedIsTrue();

    }

    @Test
    void canGetByEmail() {

        int id = 1;
        String firstName = "firstName";
        String lastName = "lastName";
        String email = "email@gmail.com";
        Customer customer = new Customer();
        customer.setId(id);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setConfirmed(true);

        when(customerDao.findByConfirmedIsTrueAndEmail(email)).thenReturn(customer);

        DataResult<Customer> expected = customerManager.getByEmail(email);

        assertThat(expected.isSuccess()).isTrue();
        assertThat(expected.getData().getId()).isEqualTo(id);
        assertThat(expected.getData().getFirstName()).isEqualTo(firstName);
        assertThat(expected.getData().getLastName()).isEqualTo(lastName);
        assertThat(expected.getData().getEmail()).isEqualTo(email);
        assertThat(expected.getData().isConfirmed()).isTrue();

    }

}
package com.osc.ecommerce.business.concretes;

import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.dal.CustomerDao;
import com.osc.ecommerce.entities.concretes.Customer;
import com.osc.ecommerce.entities.dtos.CustomerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

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
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        customerManager = new CustomerManager(customerDao, modelMapper);
    }

    @Test
    void canSave() {

        CustomerDto customerDto = new CustomerDto(
                "firstName",
                "lastName",
                "email@gmail.com",
                "12345678"
        );

        customerManager.save(customerDto);

        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).save(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();
        CustomerDto capturedCustomerDto = modelMapper.map(capturedCustomer, CustomerDto.class);
        assertThat(capturedCustomer).isEqualTo(capturedCustomerDto);

    }

    @Test
    void canGetById() {

        int id = 1;
        String firstName = "firstName";
        String lastName = "lastName";
        Customer customer = new Customer(id, firstName, lastName);

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
        Customer customer = new Customer(id, firstName, lastName);
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
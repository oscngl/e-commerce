package com.osc.ecommerce.business.abstracts;

import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.entities.concretes.Customer;
import com.osc.ecommerce.entities.dtos.CustomerDto;

import java.util.List;

public interface CustomerService {

    DataResult<String> save(CustomerDto customerDto);
    DataResult<Customer> getById(int id);
    DataResult<List<Customer>> getAll();
    DataResult<Customer> getByEmail(String email);

}

package com.osc.ecommerce.business.concretes;

import com.osc.ecommerce.business.abstracts.CustomerService;
import com.osc.ecommerce.core.utilities.results.*;
import com.osc.ecommerce.dal.CustomerDao;
import com.osc.ecommerce.entities.concretes.Customer;
import com.osc.ecommerce.entities.dtos.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerManager implements CustomerService {

    private final CustomerDao customerDao;
    private final ModelMapper modelMapper;

    @Override
    public Result save(CustomerDto customerDto) {
        Customer exists = customerDao.findByConfirmedIsTrueAndEmail(customerDto.getEmail());
        if(exists != null) {
            return new ErrorResult("Email already taken!");
        } else {
            Customer customer = modelMapper.map(customerDto, Customer.class);
            customerDao.save(customer);
            return new SuccessResult("Customer saved.");
        }
    }

    @Override
    public DataResult<Customer> getById(int id) {
        Customer customer = customerDao.findById(id).orElse(null);
        if(customer == null) {
            return new ErrorDataResult<>("Not found!");
        } else {
            return new SuccessDataResult<>(customer);
        }
    }

    @Override
    public DataResult<List<Customer>> getAll() {
        return new SuccessDataResult<>(customerDao.findAllByConfirmedIsTrue());
    }

    @Override
    public DataResult<Customer> getByEmail(String email) {
        Customer customer = customerDao.findByConfirmedIsTrueAndEmail(email);
        if(customer == null) {
            return new ErrorDataResult<>("Not found!");
        } else {
            return new SuccessDataResult<>(customer);
        }
    }

}

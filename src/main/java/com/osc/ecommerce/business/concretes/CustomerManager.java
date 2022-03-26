package com.osc.ecommerce.business.concretes;

import com.osc.ecommerce.business.abstracts.ConfirmationTokenService;
import com.osc.ecommerce.business.abstracts.CustomerService;
import com.osc.ecommerce.business.abstracts.RoleService;
import com.osc.ecommerce.business.abstracts.UserService;
import com.osc.ecommerce.core.utilities.results.*;
import com.osc.ecommerce.dal.abstracts.CustomerDao;
import com.osc.ecommerce.entities.abstracts.User;
import com.osc.ecommerce.entities.concretes.ConfirmationToken;
import com.osc.ecommerce.entities.concretes.Customer;
import com.osc.ecommerce.entities.dtos.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerManager implements CustomerService {

    private final CustomerDao customerDao;
    private final UserService userService;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public DataResult<String> save(CustomerDto customerDto) {
        DataResult<User> exists = userService.getByConfirmedEmail(customerDto.getEmail());
        if (exists != null && exists.isSuccess() && exists.getData() != null) {
            return new ErrorDataResult<>(null, "Email already taken!");
        }
        Customer customer = modelMapper.map(customerDto, Customer.class);
        String encodedPassword = bCryptPasswordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);
        customer.getRoles().add(roleService.getByName("ROLE_CUSTOMER").getData());
        customerDao.save(customer);
        ConfirmationToken confirmationToken = new ConfirmationToken(customer);
        confirmationTokenService.save(confirmationToken);
        return new SuccessDataResult<>(confirmationToken.getToken(), "Customer saved.");
    }

    @Override
    public DataResult<Customer> getById(int id) {
        Customer customer = customerDao.findById(id).orElse(null);
        if (customer == null) {
            return new ErrorDataResult<>("Not found!");
        }
        return new SuccessDataResult<>(customer);
    }

    @Override
    public DataResult<List<Customer>> getAll() {
        return new SuccessDataResult<>(customerDao.findAllByConfirmedIsTrue());
    }

    @Override
    public DataResult<Customer> getByEmail(String email) {
        Customer customer = customerDao.findByConfirmedIsTrueAndEmail(email);
        if (customer == null) {
            return new ErrorDataResult<>("Not found!");
        }
        return new SuccessDataResult<>(customer);
    }

}

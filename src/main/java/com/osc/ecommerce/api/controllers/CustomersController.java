package com.osc.ecommerce.api.controllers;

import com.osc.ecommerce.business.abstracts.CustomerService;
import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.core.utilities.results.Result;
import com.osc.ecommerce.entities.concretes.Customer;
import com.osc.ecommerce.entities.dtos.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomersController {

    private final CustomerService customerService;

    @PostMapping("/save")
    public Result save(@RequestBody CustomerDto customerDto) {
        return this.customerService.save(customerDto);
    }

    @GetMapping("/getById")
    public DataResult<Customer> getById(int id) {
        return this.customerService.getById(id);
    }
    @GetMapping("/getAll")
    public DataResult<List<Customer>> getAll() {
        return this.customerService.getAll();
    }

    @GetMapping("/getByEmail")
    public DataResult<Customer> getByEmail(String email) {
        return this.customerService.getByEmail(email);
    }

}

package com.osc.ecommerce.api.controllers;

import com.osc.ecommerce.business.abstracts.AuthService;
import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.core.utilities.results.Result;
import com.osc.ecommerce.entities.dtos.AdminDto;
import com.osc.ecommerce.entities.dtos.CustomerDto;
import com.osc.ecommerce.entities.dtos.SupplierDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register/admin")
    public DataResult<String> registerAdmin(@RequestBody AdminDto adminDto) {
        return authService.registerAdmin(adminDto);
    }

    @PostMapping("/register/customer")
    public DataResult<String> registerCustomer(@RequestBody CustomerDto customerDto) {
        return authService.registerCustomer(customerDto);
    }

    @PostMapping("/register/supplier")
    public DataResult<String> registerSupplier(@RequestBody  SupplierDto supplierDto) {
        return authService.registerSupplier(supplierDto);
    }

    @GetMapping("/confirm")
    public Result confirmToken(@RequestParam("token") String token) {
        return authService.confirm(token);
    }

}

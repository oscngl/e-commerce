package com.osc.ecommerce.api.controllers;

import com.osc.ecommerce.business.abstracts.AdminService;
import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.core.utilities.results.Result;
import com.osc.ecommerce.entities.concretes.Admin;
import com.osc.ecommerce.entities.dtos.AdminDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/admins")
@RequiredArgsConstructor
public class AdminsController {

    private final AdminService adminService;

    @PostMapping("/save")
    public Result save(@RequestBody @Valid AdminDto adminDto) {
        return this.adminService.save(adminDto);
    }

    @GetMapping("/getById")
    public DataResult<Admin> getById(int id) {
        return this.adminService.getById(id);
    }

    @GetMapping("/getAll")
    public DataResult<List<Admin>> getAll() {
        return this.adminService.getAll();
    }

    @GetMapping("/getByEmail")
    public DataResult<Admin> getByEmail(String email) {
        return this.adminService.getByEmail(email);
    }

}

package com.osc.ecommerce.api.controllers;

import com.osc.ecommerce.business.abstracts.SupplierService;
import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.core.utilities.results.Result;
import com.osc.ecommerce.entities.concretes.Supplier;
import com.osc.ecommerce.entities.dtos.SupplierDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SuppliersController {

    private final SupplierService supplierService;

    @PostMapping("/save")
    public Result save(@RequestBody SupplierDto supplierDto) {
        return this.supplierService.save(supplierDto);
    }

    @GetMapping("/getById")
    public DataResult<Supplier> getById(int id) {
        return this.supplierService.getById(id);
    }

    @GetMapping("/getAll")
    public DataResult<List<Supplier>> getAll() {
        return this.supplierService.getAll();
    }

    @GetMapping("/getByEmail")
    public DataResult<Supplier> getByEmail(String email) {
        return this.supplierService.getByEmail(email);
    }

}

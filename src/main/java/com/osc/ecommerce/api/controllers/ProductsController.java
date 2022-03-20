package com.osc.ecommerce.api.controllers;

import com.osc.ecommerce.business.abstracts.ProductService;
import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.core.utilities.results.Result;
import com.osc.ecommerce.entities.concretes.Product;
import com.osc.ecommerce.entities.dtos.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductService productService;

    @PostMapping("/save")
    public Result save(@RequestBody ProductDto productDto) {
        return this.productService.save(productDto);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Product product) {
        return this.productService.update(product);
    }

    @GetMapping("/getById")
    public DataResult<Product> getById(int id) {
        return this.productService.getById(id);
    }

    @GetMapping("/getAll")
    public DataResult<List<Product>> getAll() {
        return this.productService.getAll();
    }

    @GetMapping("/getAllByCategoryId")
    public DataResult<List<Product>> getAllByCategoryId(int categoryId) {
        return this.productService.getAllByCategoryId(categoryId);
    }

    @GetMapping("/getAllBySupplierId")
    public DataResult<List<Product>> getAllBySupplierId(int supplierId) {
        return this.productService.getAllBySupplierId(supplierId);
    }

}

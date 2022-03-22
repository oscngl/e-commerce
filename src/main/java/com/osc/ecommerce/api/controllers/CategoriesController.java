package com.osc.ecommerce.api.controllers;

import com.osc.ecommerce.business.abstracts.CategoryService;
import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.core.utilities.results.Result;
import com.osc.ecommerce.entities.concretes.Category;
import com.osc.ecommerce.entities.dtos.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoriesController {

    private final CategoryService categoryService;

    @PostMapping("/save")
    public Result save(@RequestBody @Valid CategoryDto categoryDto) {
        return this.categoryService.save(categoryDto);
    }

    @GetMapping("/getById")
    public DataResult<Category> getById(int id) {
        return this.categoryService.getById(id);
    }

    @GetMapping("/getAll")
    public DataResult<List<Category>> getAll() {
        return this.categoryService.getAll();
    }

    @GetMapping("/getByName")
    public DataResult<Category> getByName(String name) {
        return this.categoryService.getByName(name);
    }

}

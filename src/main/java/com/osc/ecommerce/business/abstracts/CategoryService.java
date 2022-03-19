package com.osc.ecommerce.business.abstracts;

import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.core.utilities.results.Result;
import com.osc.ecommerce.entities.concretes.Category;
import com.osc.ecommerce.entities.dtos.CategoryDto;

import java.util.List;

public interface CategoryService {

    Result save(CategoryDto categoryDto);
    DataResult<Category> getById(int id);
    DataResult<List<Category>> getAll();
    DataResult<Category> getByName(String name);

}

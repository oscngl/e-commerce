package com.osc.ecommerce.business.concretes;

import com.osc.ecommerce.business.abstracts.CategoryService;
import com.osc.ecommerce.core.utilities.results.*;
import com.osc.ecommerce.dal.abstracts.CategoryDao;
import com.osc.ecommerce.entities.concretes.Category;
import com.osc.ecommerce.entities.dtos.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryManager implements CategoryService {

    private final CategoryDao categoryDao;
    private final ModelMapper modelMapper;

    @Override
    public Result save(CategoryDto categoryDto) {
        Category exists = categoryDao.findByName(categoryDto.getName());
        if(exists != null) {
            return new ErrorResult("Category already exists!");
        } else {
            Category category = modelMapper.map(categoryDto, Category.class);
            categoryDao.save(category);
            return new SuccessResult("Category saved.");
        }
    }

    @Override
    public DataResult<Category> getById(int id) {
        Category category = categoryDao.findById(id).orElse(null);
        if(category == null) {
            return new ErrorDataResult<>("Not found!");
        } else {
            return new SuccessDataResult<>(category);
        }
    }

    @Override
    public DataResult<List<Category>> getAll() {
        return new SuccessDataResult<>(categoryDao.findAll());
    }

    @Override
    public DataResult<Category> getByName(String name) {
        Category category = categoryDao.findByName(name);
        if(category == null) {
            return new ErrorDataResult<>("Not found!");
        } else {
            return new SuccessDataResult<>(category);
        }
    }

}

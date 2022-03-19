package com.osc.ecommerce.business.concretes;

import com.osc.ecommerce.business.abstracts.ProductService;
import com.osc.ecommerce.core.utilities.results.*;
import com.osc.ecommerce.dal.ProductDao;
import com.osc.ecommerce.entities.concretes.Product;
import com.osc.ecommerce.entities.dtos.ProductDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductManager implements ProductService {

    private final ProductDao productDao;
    private final ModelMapper modelMapper;

    @Override
    public Result save(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        productDao.save(product);
        return new SuccessResult("Product saved.");
    }

    @Override
    public Result update(Product product) {
        Product exists = productDao.findById(product.getId()).get();
        if(exists.getName() == null) {
            return new ErrorResult("Not found!");
        } else {
            productDao.save(product);
            return new SuccessResult("Product updated.");
        }
    }

    @Override
    public DataResult<Product> getById(int id) {
        Product product = productDao.findById(id).get();
        if(product.getName() == null) {
            return new ErrorDataResult<>("Not found!");
        } else {
            return new SuccessDataResult<>(product);
        }
    }

    @Override
    public DataResult<List<Product>> getAll() {
        return new SuccessDataResult<>(productDao.findAll());
    }

    @Override
    public DataResult<List<Product>> getAllByCategoryId(int categoryId) {
        return new SuccessDataResult<>(productDao.findAllByCategory_Id(categoryId));
    }

    @Override
    public DataResult<List<Product>> getAllBySupplierId(int supplierId) {
        return new SuccessDataResult<>(productDao.findAllBySupplier_Id(supplierId));
    }

}
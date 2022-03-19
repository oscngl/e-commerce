package com.osc.ecommerce.business.abstracts;

import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.core.utilities.results.Result;
import com.osc.ecommerce.entities.concretes.Product;
import com.osc.ecommerce.entities.dtos.ProductDto;

import java.util.List;

public interface ProductService {

    Result save(ProductDto productDto);
    Result update(Product product);
    DataResult<Product> getById(int id);
    DataResult<List<Product>> getAll();
    DataResult<List<Product>> getAllByCategoryId(int categoryId);
    DataResult<List<Product>> getAllBySupplierId(int supplierId);

}

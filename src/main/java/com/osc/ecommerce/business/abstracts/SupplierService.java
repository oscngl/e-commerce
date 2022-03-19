package com.osc.ecommerce.business.abstracts;

import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.core.utilities.results.Result;
import com.osc.ecommerce.entities.concretes.Supplier;
import com.osc.ecommerce.entities.dtos.SupplierDto;

import java.util.List;

public interface SupplierService {

    Result save(SupplierDto supplierDto);
    DataResult<Supplier> getById(int id);
    DataResult<List<Supplier>> getAll();
    DataResult<Supplier> getByEmail(String email);

}

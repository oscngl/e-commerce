package com.osc.ecommerce.business.abstracts;

import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.core.utilities.results.Result;
import com.osc.ecommerce.entities.dtos.AdminDto;
import com.osc.ecommerce.entities.dtos.CustomerDto;
import com.osc.ecommerce.entities.dtos.SupplierDto;

public interface AuthService {

    DataResult<String> registerAdmin(AdminDto adminDto);
    DataResult<String> registerCustomer(CustomerDto customerDto);
    DataResult<String> registerSupplier(SupplierDto supplierDto);
    Result confirm(String token);

}

package com.osc.ecommerce.business.abstracts;

import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.entities.concretes.Admin;
import com.osc.ecommerce.entities.dtos.AdminDto;

import java.util.List;

public interface AdminService {

    DataResult<String> save(AdminDto adminDto);
    DataResult<Admin> getById(int id);
    DataResult<List<Admin>> getAll();
    DataResult<Admin> getByEmail(String email);

}

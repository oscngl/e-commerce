package com.osc.ecommerce.business.abstracts;

import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.core.utilities.results.Result;
import com.osc.ecommerce.entities.concretes.Role;
import com.osc.ecommerce.entities.dtos.RoleDto;

public interface RoleService {

    Result save(RoleDto roleDto);
    DataResult<Role> getByName(String name);

}

package com.osc.ecommerce.business.concretes;

import com.osc.ecommerce.business.abstracts.RoleService;
import com.osc.ecommerce.core.utilities.results.*;
import com.osc.ecommerce.dal.abstracts.RoleDao;
import com.osc.ecommerce.entities.concretes.Role;
import com.osc.ecommerce.entities.dtos.RoleDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleManager implements RoleService {

    private final RoleDao roleDao;
    private final ModelMapper modelMapper;

    @Override
    public Result save(RoleDto roleDto) {
        Role role = modelMapper.map(roleDto, Role.class);
        roleDao.save(role);
        return new SuccessResult("Role saved.");
    }

    @Override
    public DataResult<Role> getByName(String name) {
        Role role = roleDao.findByName(name);
        if (role == null) {
            return new ErrorDataResult<>(null, "Role not found!");
        }
        return new SuccessDataResult<>(role);
    }

}

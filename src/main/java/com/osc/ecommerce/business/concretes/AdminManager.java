package com.osc.ecommerce.business.concretes;

import com.osc.ecommerce.business.abstracts.AdminService;
import com.osc.ecommerce.core.utilities.results.*;
import com.osc.ecommerce.dal.AdminDao;
import com.osc.ecommerce.entities.concretes.Admin;
import com.osc.ecommerce.entities.dtos.AdminDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminManager implements AdminService {

    private final AdminDao adminDao;
    private final ModelMapper modelMapper;

    @Override
    public Result save(AdminDto adminDto) {
        Admin exists = adminDao.findByConfirmedIsTrueAndEmail(adminDto.getEmail());
        if(exists != null) {
            return new ErrorResult("Email already taken!");
        } else {
            Admin admin = modelMapper.map(adminDto, Admin.class);
            adminDao.save(admin);
            return new SuccessResult("Admin saved.");
        }
    }

    @Override
    public DataResult<Admin> getById(int id) {
        Admin admin = adminDao.findById(id).orElse(null);
        if(admin == null) {
            return new ErrorDataResult<>("Not found!");
        } else {
            return new SuccessDataResult<>(admin);
        }
    }

    @Override
    public DataResult<List<Admin>> getAll() {
        return new SuccessDataResult<>(adminDao.findAllByConfirmedIsTrue());
    }

    @Override
    public DataResult<Admin> getByEmail(String email) {
        Admin admin = adminDao.findByConfirmedIsTrueAndEmail(email);
        if(admin == null) {
            return new ErrorDataResult<>("Not found!");
        } else {
            return new SuccessDataResult<>(admin);
        }
    }

}

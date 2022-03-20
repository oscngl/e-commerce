package com.osc.ecommerce.business.concretes;

import com.osc.ecommerce.business.abstracts.SupplierService;
import com.osc.ecommerce.core.utilities.results.*;
import com.osc.ecommerce.dal.SupplierDao;
import com.osc.ecommerce.entities.concretes.Supplier;
import com.osc.ecommerce.entities.dtos.SupplierDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierManager implements SupplierService {

    private final SupplierDao supplierDao;
    private final ModelMapper modelMapper;

    @Override
    public Result save(SupplierDto supplierDto) {
        Supplier exists = supplierDao.findByConfirmedIsTrueAndEmail(supplierDto.getEmail());
        if(exists != null) {
            return new ErrorResult("Email already taken!");
        } else {
            Supplier supplier = modelMapper.map(supplierDto, Supplier.class);
            supplierDao.save(supplier);
            return new SuccessResult("Supplier saved.");
        }
    }

    @Override
    public DataResult<Supplier> getById(int id) {
        Supplier supplier = supplierDao.findById(id).orElse(null);
        if(supplier == null) {
            return new ErrorDataResult<>("Not found!");
        } else {
            return new SuccessDataResult<>(supplier);
        }
    }

    @Override
    public DataResult<List<Supplier>> getAll() {
        return new SuccessDataResult<>(supplierDao.findAllByConfirmedIsTrue());
    }

    @Override
    public DataResult<Supplier> getByEmail(String email) {
        Supplier supplier = supplierDao.findByConfirmedIsTrueAndEmail(email);
        if(supplier == null) {
            return new ErrorDataResult<>("Not found!");
        } else {
            return new SuccessDataResult<>(supplier);
        }
    }

}

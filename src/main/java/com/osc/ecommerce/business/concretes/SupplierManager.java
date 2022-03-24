package com.osc.ecommerce.business.concretes;

import com.osc.ecommerce.business.abstracts.ConfirmationTokenService;
import com.osc.ecommerce.business.abstracts.RoleService;
import com.osc.ecommerce.business.abstracts.SupplierService;
import com.osc.ecommerce.business.abstracts.UserService;
import com.osc.ecommerce.core.utilities.results.*;
import com.osc.ecommerce.dal.abstracts.SupplierDao;
import com.osc.ecommerce.entities.abstracts.User;
import com.osc.ecommerce.entities.concretes.ConfirmationToken;
import com.osc.ecommerce.entities.concretes.Supplier;
import com.osc.ecommerce.entities.dtos.SupplierDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierManager implements SupplierService {

    private final SupplierDao supplierDao;
    private final UserService userService;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public DataResult<String> save(SupplierDto supplierDto) {
        DataResult<User> exists = userService.getByConfirmedEmail(supplierDto.getEmail());
        if(exists.isSuccess() && exists.getData() != null) {
            return new ErrorDataResult<>(null, "Email already taken!");
        } else {
            Supplier supplier = modelMapper.map(supplierDto, Supplier.class);
            String encodedPassword = bCryptPasswordEncoder.encode(supplier.getPassword());
            supplier.setPassword(encodedPassword);
            supplier.getRoles().add(roleService.getByName("SUPPLIER").getData());
            supplierDao.save(supplier);
            ConfirmationToken confirmationToken = new ConfirmationToken(supplier);
            confirmationTokenService.save(confirmationToken);
            return new SuccessDataResult<>(confirmationToken.getToken(), "Supplier saved.");
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

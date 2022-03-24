package com.osc.ecommerce.business.concretes;

import com.osc.ecommerce.business.abstracts.UserService;
import com.osc.ecommerce.core.utilities.results.*;
import com.osc.ecommerce.dal.abstracts.UserDao;
import com.osc.ecommerce.entities.abstracts.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserManager implements UserService, UserDetailsService {

    private final UserDao userDao;

    @Override
    public Result confirm(int id) {
        User user = userDao.findById(id).orElse(null);
        if(user == null) {
            return new ErrorResult("User not found!");
        } else {
            user.setConfirmed(true);
            userDao.save(user);
            return new SuccessResult("User confirmed.");
        }
    }

    @Override
    public DataResult<User> getByConfirmedEmail(String email) {
        User user = userDao.findByConfirmedIsTrueAndEmail(email);
        if(user == null) {
            return new ErrorDataResult<>(null, "User not found");
        } else {
            return new SuccessDataResult<>(user);
        }
    }

    @Override
    public DataResult<User> getByEmail(String email) {
        User user = userDao.findByEmail(email);
        if(user == null) {
            return new ErrorDataResult<>(null, "User not found");
        } else {
            return new SuccessDataResult<>(user);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userDao.findByEmail(email);
    }
}

package com.osc.ecommerce.business.concretes;

import com.osc.ecommerce.business.abstracts.UserService;
import com.osc.ecommerce.core.utilities.results.ErrorResult;
import com.osc.ecommerce.core.utilities.results.Result;
import com.osc.ecommerce.core.utilities.results.SuccessResult;
import com.osc.ecommerce.dal.UserDao;
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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userDao.findByEmail(email);
    }

}

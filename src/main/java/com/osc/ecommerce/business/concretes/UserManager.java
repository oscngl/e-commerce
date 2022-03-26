package com.osc.ecommerce.business.concretes;

import com.osc.ecommerce.business.abstracts.UserService;
import com.osc.ecommerce.core.utilities.results.*;
import com.osc.ecommerce.dal.abstracts.UserDao;
import com.osc.ecommerce.entities.abstracts.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserManager implements UserService, UserDetailsService {

    private final UserDao userDao;

    @Override
    public Result confirm(int id) {
        User user = userDao.findById(id).orElse(null);
        if (user == null) {
            return new ErrorResult("User not found!");
        }
        user.setConfirmed(true);
        userDao.save(user);
        return new SuccessResult("User confirmed.");
    }

    @Override
    public DataResult<User> getByConfirmedEmail(String email) {
        User user = userDao.findByConfirmedIsTrueAndEmail(email);
        if (user == null) {
            return new ErrorDataResult<>(null, "User not found");
        }
        return new SuccessDataResult<>(user);
    }

    @Override
    public DataResult<User> getByEmail(String email) {
        User user = userDao.findByEmail(email);
        if (user == null) {
            return new ErrorDataResult<>(null, "User not found");
        }
        return new SuccessDataResult<>(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findByConfirmedIsTrueAndEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found in the database!");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

}

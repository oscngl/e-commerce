package com.osc.ecommerce.dal;

import com.osc.ecommerce.entities.abstracts.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    User findByEmail(String email);
    List<User> findAllByConfirmedIsTrue();

}

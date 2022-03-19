package com.osc.ecommerce.dal;

import com.osc.ecommerce.entities.abstracts.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    User findByEmail(String email);
    List<User> findAllByConfirmedIsTrue();

    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.confirmed = TRUE" +
            " WHERE a.email = ?1")
    int confirmUser(String email);
}

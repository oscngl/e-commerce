package com.osc.ecommerce.dal;

import com.osc.ecommerce.entities.concretes.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AdminDao extends JpaRepository<Admin, Integer> {

    Admin findByEmail(String email);
    List<Admin> findAllByConfirmedIsTrue();

    @Transactional
    @Modifying
    @Query("UPDATE Admin a " +
            "SET a.confirmed = TRUE" +
            " WHERE a.email = ?1")
    int confirmAdmin(String email);
}

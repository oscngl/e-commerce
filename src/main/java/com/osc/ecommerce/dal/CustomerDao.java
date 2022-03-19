package com.osc.ecommerce.dal;

import com.osc.ecommerce.entities.concretes.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer> {

    Customer findByEmail(String email);
    List<Customer> findAllByConfirmedIsTrue();

    @Transactional
    @Modifying
    @Query("UPDATE Customer a " +
            "SET a.confirmed = TRUE" +
            " WHERE a.email = ?1")
    int confirmCustomer(String email);
}

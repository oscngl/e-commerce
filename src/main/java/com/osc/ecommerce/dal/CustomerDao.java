package com.osc.ecommerce.dal;

import com.osc.ecommerce.entities.concretes.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer> {

    Customer findByEmail(String email);
    List<Customer> findAllByConfirmedIsTrue();

}

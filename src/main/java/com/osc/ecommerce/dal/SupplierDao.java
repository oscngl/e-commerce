package com.osc.ecommerce.dal;

import com.osc.ecommerce.entities.concretes.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierDao extends JpaRepository<Supplier, Integer> {

    Supplier findByEmail(String email);
    List<Supplier> findAllByConfirmedIsTrue();

}

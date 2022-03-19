package com.osc.ecommerce.dal;

import com.osc.ecommerce.entities.concretes.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SupplierDao extends JpaRepository<Supplier, Integer> {

    Supplier findByEmail(String email);
    List<Supplier> findAllByConfirmedIsTrue();

    @Transactional
    @Modifying
    @Query("UPDATE Supplier a " +
            "SET a.confirmed = TRUE" +
            " WHERE a.email = ?1")
    int confirmSupplier(String email);
}

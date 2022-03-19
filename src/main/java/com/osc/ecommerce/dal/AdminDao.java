package com.osc.ecommerce.dal;

import com.osc.ecommerce.entities.concretes.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminDao extends JpaRepository<Admin, Integer> {

    Admin findByConfirmedIsTrueAndEmail(String email);
    List<Admin> findAllByConfirmedIsTrue();

}

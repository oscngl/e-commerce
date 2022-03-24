package com.osc.ecommerce.dal.abstracts;

import com.osc.ecommerce.entities.concretes.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenDao extends JpaRepository<ConfirmationToken, Integer> {

    ConfirmationToken findByToken(String token);

}

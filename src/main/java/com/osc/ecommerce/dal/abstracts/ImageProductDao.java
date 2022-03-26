package com.osc.ecommerce.dal.abstracts;

import com.osc.ecommerce.entities.concretes.ImageProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageProductDao extends JpaRepository<ImageProduct, Integer> {

}

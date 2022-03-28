package com.osc.ecommerce.dal.abstracts;

import com.osc.ecommerce.entities.concretes.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {

    List<Product> findAllByEnabledIsTrue();
    List<Product> findAllByEnabledIsTrueAndCategory_Id(int categoryId);
    List<Product> findAllByEnabledIsTrueAndSupplier_Id(int supplierId);

}

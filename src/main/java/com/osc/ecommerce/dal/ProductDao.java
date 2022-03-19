package com.osc.ecommerce.dal;

import com.osc.ecommerce.entities.concretes.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {

    List<Product> findAllByCategory_Id(int categoryId);
    List<Product> findAllBySupplier_Id(int supplierId);

}

package com.osc.ecommerce.dal.abstracts;

import com.osc.ecommerce.entities.concretes.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDao extends JpaRepository<Category, Integer> {

    Category findByName(String name);

}

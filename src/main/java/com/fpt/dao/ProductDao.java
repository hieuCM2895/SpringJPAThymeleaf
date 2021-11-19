package com.fpt.dao;

import com.fpt.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
/*
 * Product Dao extends Jpa Repository
 */
@Repository
@Transactional
public interface ProductDao extends JpaRepository<Product, Long> {
    /*
     * @Method: findProductById
     * @Query by method name
     * @param: product id
     *
     * @return: product
     */
    Product findProductById(int id);
}

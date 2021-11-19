package com.fpt.dao;

import com.fpt.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
/*
 * Category Dao extends Jpa Repository
 */
@Repository
@Transactional
public interface CategoryDao extends JpaRepository<Category, Long> {
}

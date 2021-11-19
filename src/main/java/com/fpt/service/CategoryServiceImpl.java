package com.fpt.service;

import com.fpt.dao.CategoryDao;
import com.fpt.dto.CategoryDTO;
import com.fpt.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CategoryDTO categoryDTO;

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        return categoryDTO.transferCategoryToCategoryDTO(categoryDao.save(categoryDTO.transferCategoryDTOToCategory(categoryDTO)));
    }

    @Override
    public void deleteCategory(long id) {
        categoryDao.deleteById(id);
    }

    @Override
    public Boolean updateCategory(CategoryDTO categoryDTO) {
        return categoryDao.save(categoryDTO.transferCategoryDTOToCategory(categoryDTO)) != null;
    }

    @Override
    public List<CategoryDTO> findAllCategory() {
        List<CategoryDTO> listOfCategoryDTO = new ArrayList<>();
        List<Category> list = categoryDao.findAll();
        for (Category category : list) {
            listOfCategoryDTO.add(categoryDTO.transferCategoryToCategoryDTO(category));
        }
        return listOfCategoryDTO;
    }

    @Override
    public CategoryDTO findCategoryById(long id) {
        Category category = categoryDao.findById(id).orElseThrow(() -> new UsernameNotFoundException("Category not found with id: " + id));
        return categoryDTO.transferCategoryToCategoryDTO(category);
    }
}

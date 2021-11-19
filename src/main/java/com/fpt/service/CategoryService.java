package com.fpt.service;

import com.fpt.dto.CategoryDTO;
import java.util.List;

public interface CategoryService {

    CategoryDTO saveCategory(CategoryDTO categoryDTO);

    void deleteCategory(long id);

    Boolean updateCategory(CategoryDTO categoryDTO);

    List<CategoryDTO> findAllCategory();

    CategoryDTO findCategoryById(long id);
}

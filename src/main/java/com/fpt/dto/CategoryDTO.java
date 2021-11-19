package com.fpt.dto;

import com.fpt.model.Category;
import com.fpt.model.Product;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;


@Data
public class CategoryDTO {

    private int id;
    private String name;

    public CategoryDTO transferCategoryToCategoryDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }

    public Category transferCategoryDTOToCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        return category;
    }
}

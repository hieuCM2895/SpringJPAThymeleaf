package com.fpt.dto;

import com.fpt.model.Category;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProductDTO {

    @NotNull
    private Category category;

    @NotNull
    private String name;

    private String image;

    private String title;

    private String description;
}
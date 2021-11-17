package com.fpt.dto;

import com.fpt.model.Product;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;


@Data
public class CategoryDTO {

    @NotNull
    private String name;

    private Set<Product> listOfProduct;
}

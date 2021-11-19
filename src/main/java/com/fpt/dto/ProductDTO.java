package com.fpt.dto;

import com.fpt.model.Product;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component
public class ProductDTO {

    private int id;
    private CategoryDTO categoryDTO;
    private String name;
    private String image;
    private double price;
    private String title;
    private String description;
    private transient int amount = 1;

    @Autowired
    private CategoryDTO category;

    public ProductDTO transferProductToProductDTO(Product product) {

        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(product.getId());
        productDTO.setAmount(product.getAmount());
        productDTO.setCategoryDTO(category.transferCategoryToCategoryDTO(product.getCategory()));
        productDTO.setPrice(product.getPrice());
        productDTO.setDescription(product.getDescription());
        productDTO.setImage(product.getImage());
        productDTO.setName(product.getName());
        productDTO.setTitle(product.getTitle());

        return productDTO;
    }

    public Product transferProductDTOToProduct(ProductDTO productDTO) {

        Product product = new Product();

        product.setId(productDTO.getId());
        product.setAmount(productDTO.getAmount());
        product.setCategory(category.transferCategoryDTOToCategory(productDTO.getCategory()));
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setImage(productDTO.getImage());
        product.setName(productDTO.getName());
        product.setTitle(productDTO.getTitle());

        return product;
    }
}
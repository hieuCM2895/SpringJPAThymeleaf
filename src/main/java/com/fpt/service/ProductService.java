package com.fpt.service;

import com.fpt.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    ProductDTO saveProduct(ProductDTO productDTO);

    void deleteProduct(long id);

    void updateProduct(ProductDTO productDTO);

    List<ProductDTO> findAllProduct();

    ProductDTO findProductById(int id);

    List<ProductDTO> findALlProductByCategoryId(long id);

    List<ProductDTO> findAllProductByCategoryName(String nameOfCategory);

    List<ProductDTO> paginationForProduct(int currentNumberPage, int sizePage);

    List<ProductDTO> paginationForProduct(int currentNumberPage, int sizePage, int min, int max);

    Object getAmountOfAllProduct();

    Object getAmountOfAllProductByFilterPrice(int min, int max);

    List<ProductDTO> findNewProduct();

    List<ProductDTO> paginationForProductByOrder(int currentNumberPage, int sizePage, String status);

    Object findRangePriceOfProduct(String range);

}

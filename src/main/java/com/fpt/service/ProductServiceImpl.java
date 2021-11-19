package com.fpt.service;

import com.fpt.dao.ProductDao;
import com.fpt.dao.ProductDaoImpl;
import com.fpt.dto.ProductDTO;
import com.fpt.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductDTO productDTO;

    @Autowired
    private ProductDaoImpl productDaoImpl;

    public List<ProductDTO> transferListProductToListProductDTO(List<Product> listProduct) {
        List<ProductDTO> listOfProductDTO = new ArrayList<>();
        for (Product product : listProduct) {
            listOfProductDTO.add(productDTO.transferProductToProductDTO(product));
        }
        return listOfProductDTO;
    }

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = productDao.save(productDTO.transferProductDTOToProduct(productDTO));
        return productDTO.transferProductToProductDTO(product);
    }

    @Override
    public void deleteProduct(long id) {
        productDao.deleteById(id);
    }

    @Override
    public void updateProduct(ProductDTO productDTO) {
        productDao.save(productDTO.transferProductDTOToProduct(productDTO));
    }

    @Override
    public List<ProductDTO> findAllProduct() {
        return transferListProductToListProductDTO(productDao.findAll());
    }

    @Override
    public ProductDTO findProductById(int id) {
        return productDTO.transferProductToProductDTO(productDao.findProductById(id));
    }

    @Override
    public List<ProductDTO> findALlProductByCategoryId(long id) {
        return transferListProductToListProductDTO(productDaoImpl.findAllProductByCategoryId(id));
    }

    @Override
    public List<ProductDTO> findAllProductByCategoryName(String nameOfCategory) {
        return transferListProductToListProductDTO(productDaoImpl.findNewProductByCategory(nameOfCategory));
    }

    @Override
    public List<ProductDTO> paginationForProduct(int currentNumberPage, int sizePages) {
        return transferListProductToListProductDTO(productDaoImpl.paginationForProduct(currentNumberPage, sizePages));
    }

    @Override
    public List<ProductDTO> paginationForProduct(int currentNumberPage, int sizePage, int min, int max) {
        return transferListProductToListProductDTO(productDaoImpl.paginationForProduct(currentNumberPage, sizePage, min, max));
    }

    @Override
    public Object getAmountOfAllProduct() {
        return productDaoImpl.getAmountOfAllProduct();
    }

    @Override
    public Object getAmountOfAllProductByFilterPrice(int min, int max) {
        return productDaoImpl.getAmountOfAllProductByFilterPrice(min, max);
    }

    @Override
    public List<ProductDTO> findNewProduct() {
        return transferListProductToListProductDTO(productDaoImpl.findNewProduct());
    }

    @Override
    public List<ProductDTO> paginationForProductByOrder(int currentNumberPage, int sizePage, String status) {
        return transferListProductToListProductDTO(productDaoImpl.paginationForProductByOrder(currentNumberPage, sizePage, status));
    }

    @Override
    public Object findRangePriceOfProduct(String range) {
        return productDaoImpl.findRangePriceOfProduct(range);
    }

}

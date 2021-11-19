package com.fpt.dao;

import com.fpt.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ProductDaoImpl {

    @Autowired
    protected EntityManager entityManager;

    /*
     * @Method: findAllProductByCategoryId
     * @Query criteria
     * @param: categoryid
     *
     * @return: list of products
     */
    public List<Product> findAllProductByCategoryId(long categoryId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);
        query.select(root).where(builder.equal(root.get("category").get("id"), categoryId));
        return entityManager.createQuery(query).getResultList();
    }

    /*
     * @Method: findNewProductByCategory
     * @Query criteria
     * @param: nameOfCategory
     *
     * @return: list of products
     */
    public List<Product> findNewProductByCategory(String nameOfCategory) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);

        query.select(root).where(builder.like(root.get("category").get("name"), nameOfCategory))
                .orderBy(builder.desc(root.get("id")));

        Query collect = entityManager.createQuery(query);
        collect.setFirstResult(0);
        collect.setMaxResults(4);
        return collect.getResultList();
    }

    /*
     * @Method: paginationForProduct
     * @Query criteria
     * @param: currentNumberPage and pageSize(amount products in each page)
     *
     * @return: list of products
     */
    public List<Product> paginationForProduct(int currentNumberPage, int pageSize) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);

        query.select(root).orderBy(builder.asc(root.get("id")));

        Query collect = entityManager.createQuery(query);
        collect.setFirstResult((currentNumberPage - 1) * pageSize);
        collect.setMaxResults(pageSize);
        return collect.getResultList();
    }

    /*
     * @Method: paginationForProduct
     * @Query criteria
     * @param: currentNumberPage, pageSize(amount products in each page), min-max price
     *
     * @return: list of products
     */
    public List<Product> paginationForProduct(int currentNumberPage, int pageSize, int min, int max) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);

        query.select(root).where(builder.and(builder.lessThanOrEqualTo(root.get("price"), max),
                        builder.greaterThanOrEqualTo(root.get("price"), min)))
                .orderBy(builder.asc(root.get("price")));

        Query collect = entityManager.createQuery(query);
        collect.setFirstResult((currentNumberPage - 1) * pageSize);
        collect.setMaxResults(pageSize);
        return collect.getResultList();

    }

    /*
     * @Method: paginationForProductByOrder
     * @Query criteria
     * @param: currentNumberPage, pageSize(amount products in each page), status: ASC or DESC
     *
     * @return: list of products
     */
    public List<Product> paginationForProductByOrder(int currentNumberPage, int pageSize, String status) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);

        if ("asc".equals(status)) {
            query.select(root).orderBy(builder.asc(root.get("price")));
        } else {
            query.select(root).orderBy(builder.desc(root.get("price")));
        }

        Query collect = entityManager.createQuery(query);
        collect.setFirstResult((currentNumberPage - 1) * pageSize);
        collect.setMaxResults(pageSize);
        return collect.getResultList();
    }

    /*
     * @Method: getAmountOfAllProduct
     * @Query criteria
     * @param:
     *
     * @purpose: support for product pagination
     * @return: amount (number of products)
     */
    public Object getAmountOfAllProduct() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> query = builder.createQuery(Object.class);
        Root<Product> root = query.from(Product.class);
        query.select(builder.count(root.get("id")));
        return entityManager.createQuery(query).getSingleResult();
    }

    /*
     * @Method: getAmountOfAllProductByFilterPrice
     * @Query criteria
     * @param:
     *
     * @purpose: support for product pagination when customers are using filter price
     * @return: amount (number of products)
     */
    public Object getAmountOfAllProductByFilterPrice(int min, int max) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);
        query.select(root).where(builder.and(builder.lessThanOrEqualTo(root.get("price"), max),
                builder.greaterThanOrEqualTo(root.get("price"), min)));
        Query collect = entityManager.createQuery(query);
        return collect.getResultList().size();

    }

    /*
     * @Method: findNewProduct
     * @Query criteria
     * @param: none
     *
     *
     * @return: List<Product>
     */
    public List<Product> findNewProduct() {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);

        query.select(root).orderBy(builder.desc(root.get("id")));

        Query collect = entityManager.createQuery(query);
        collect.setFirstResult(0);
        collect.setMaxResults(3);
        return collect.getResultList();

    }

    /*
     * @Method: findRangePriceOfProduct
     * @Query criteria
     * @param:  range
     *
     *
     * @return: min price or max price
     */
    public Object findRangePriceOfProduct(String range) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> query = builder.createQuery(Object.class);
        Root<Product> root = query.from(Product.class);
        if ("min".equals(range)) {
            query.select(root.get("price")).orderBy(builder.asc(root.get("price")));
        } else if ("max".equals(range)) {
            query.select(root.get("price")).orderBy(builder.desc(root.get("price")));
        }
        Query collect = entityManager.createQuery(query);
        collect.setFirstResult(0);
        collect.setMaxResults(1);
        return collect.getSingleResult();
    }

}

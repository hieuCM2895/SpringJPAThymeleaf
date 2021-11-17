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

    public List<Product> findAllProductByCategoryId(long categoryId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);
        query.select(root).where(builder.equal(root.get("category").get("id"), categoryId));
        return entityManager.createQuery(query).getResultList();
    }

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

    public Object getAmountOfAllProduct() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> query = builder.createQuery(Object.class);
        Root<Product> root = query.from(Product.class);
        query.select(builder.count(root.get("id")));
        return entityManager.createQuery(query).getSingleResult();
    }

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

}

package com.example.ecommerce.repository;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.ProductVariant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductVariantRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public ProductVariant create(ProductVariant variant) {
        entityManager.persist(variant);
        return variant;
    }

    public ProductVariant findBySku(String sku) {
        return entityManager.find(ProductVariant.class, sku);
    }

    public List<ProductVariant> findByProduct(Product product) {
        return entityManager.createQuery("SELECT v FROM ProductVariant v WHERE v.product = :product", ProductVariant.class)
                .setParameter("product", product)
                .getResultList();
    }

    public List<ProductVariant> findAll() {
        return entityManager.createQuery("SELECT v FROM ProductVariant v", ProductVariant.class)
                .getResultList();
    }

    public ProductVariant update(ProductVariant variant) {
        return entityManager.merge(variant);
    }

    public void delete(String sku) {
        ProductVariant variant = findBySku(sku);
        if (variant != null) {
            entityManager.remove(variant);
        }
    }
}

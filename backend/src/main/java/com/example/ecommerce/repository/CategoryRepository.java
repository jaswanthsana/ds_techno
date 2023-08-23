package com.example.ecommerce.repository;

import com.example.ecommerce.model.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Category create(Category category) {
        entityManager.persist(category);
        return category;
    }

    public Category findById(Long id) {
        return entityManager.find(Category.class, id);
    }

    public Category findByName(String name) {
        return entityManager.createQuery("SELECT c FROM Category c WHERE c.name = :name", Category.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public List<Category> findAll() {
        return entityManager.createQuery("SELECT c FROM Category c", Category.class)
                .getResultList();
    }

    public Category update(Category category) {
        return entityManager.merge(category);
    }

    public void delete(Long id) {
        Category category = findById(id);
        if (category != null) {
            entityManager.remove(category);
        }
    }
}

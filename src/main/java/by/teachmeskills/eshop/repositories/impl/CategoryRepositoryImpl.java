package by.teachmeskills.eshop.repositories.impl;

import by.teachmeskills.eshop.entities.Category;
import by.teachmeskills.eshop.repositories.CategoryRepository;
import org.springframework.stereotype.Repository;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Category> getAllCategories() {
        return entityManager.createQuery("select c from Category c").getResultList();
    }

    @Override
    public String getCategoryNameByID(int id) {
        Query query = entityManager.createQuery("select c.name from Category c where c.id=:id");
        query.setParameter("id", id);
        return (String) query.getSingleResult();
    }

    @Override
    public Category getCategoryById(int id) {
        return entityManager.find(Category.class, id);
    }

    //method should be updated
    @Override
    public Category create(Category entity) {
        entityManager.persist(entity);
        return entity;
    }

    //method should be updated
    @Override
    public List<Category> read() {
        return getAllCategories();
    }

    //method should be updated
    @Override
    public Category update(Category entity) {
        Category category = entityManager.find(Category.class, entity.getId());
        category.setName(category.getName());
        category.setImagePath(entity.getImagePath());
        category.setRating(entity.getRating());
        entityManager.persist(category);
        return category;
    }

    //method should be updated
    @Override
    public void delete(int id) {
        Category category = entityManager.find(Category.class, id);
        entityManager.remove(category);
    }
}
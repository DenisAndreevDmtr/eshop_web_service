package by.teachmeskills.eshop.repositories.impl;

import by.teachmeskills.eshop.entities.Category;
import by.teachmeskills.eshop.entities.Order;
import by.teachmeskills.eshop.entities.Product;
import by.teachmeskills.eshop.repositories.CategoryRepository;
import by.teachmeskills.eshop.repositories.ProductRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

import static by.teachmeskills.eshop.utils.EshopConstants.PRODUCTS_PER_PAGE;


@Transactional
@Repository
public class ProductRepositoryImpl implements ProductRepository {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Product> getAllProductsByCategoryId(int categoryId) {
        Query query = entityManager.createQuery("select p from Product p where p.category.id=:categoryId");
        query.setParameter("categoryId", categoryId);
        return query.getResultList();
    }

    @Override
    public Product getProductById(int id) {
        return entityManager.find(Product.class, id);
    }

    @Override
    public List<Product> getListProductsByNameOrDesc(String param) {
        String requestDB = '%' + param + '%';
        Query query = entityManager.createQuery("select p from Product p where p.name like :requestDB or p.description like: requestDB");
        query.setParameter("requestDB", requestDB);
        return query.getResultList();
    }

    //method should be updated
    @Override
    public Product create(Product entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public List<Product> read() {
        return entityManager.createQuery("select p from Product p").getResultList();
    }

    @Override
    public Map<Product, Integer> getAllProductsByOrderId(int orderId) {
        Order order = entityManager.find(Order.class, orderId);
        return order.getProducts();
    }

    //method should be updated
    @Override
    public Product update(Product entity) {
        Product product = entityManager.find(Product.class, entity.getId());
        product.setName(entity.getName());
        product.setImagePath(entity.getImagePath());
        product.setPrice(entity.getPrice());
        product.setDescription(entity.getDescription());
        product.setCategory(entity.getCategory());
        entityManager.persist(product);
        return product;
    }

    //method should be updated
    @Override
    public void delete(int id) {
        Product product = entityManager.find(Product.class, id);
        entityManager.remove(product);
    }

    @Override
    public long countAllProductsByCategory(int categoryId) {
        int pageSize = PRODUCTS_PER_PAGE;
        Query query = entityManager.createQuery("select count(p) from Product p where p.category.id=:categoryId");
        query.setParameter("categoryId", categoryId);
        long resultQuery = (Long) query.getSingleResult();
        if (resultQuery % pageSize != 0) {
            return resultQuery / pageSize + 1;
        }
        return resultQuery / pageSize;
    }

    @Override
    public List<Product> getAllProductsByCategoryIdPaging(int categoryId, int pageReq) {
        int pageSize = PRODUCTS_PER_PAGE;
        int firstResult;
        if (pageReq > 1) {
            firstResult = (pageReq - 1) * pageSize;
        } else {
            firstResult = 0;
        }
        Query query = entityManager.createQuery("select p from Product p where p.category.id=:categoryId order by p.name asc");
        query.setParameter("categoryId", categoryId);
        query.setFirstResult(firstResult);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public long countProductsByNameOrDesc(String param) {
        int pageSize = PRODUCTS_PER_PAGE;
        String requestDB = '%' + param + '%';
        Query query = entityManager.createQuery("select count(p) from Product p where p.name like :requestDB or p.description like: requestDB order by p.name asc");
        query.setParameter("requestDB", requestDB);
        long resultQuery = (Long) query.getSingleResult();
        if (resultQuery % pageSize != 0) {
            return resultQuery / pageSize + 1;
        }
        return resultQuery / pageSize;
    }
}
package by.teachmeskills.eshop.repositories.impl;

import by.teachmeskills.eshop.entities.BaseEntity;
import by.teachmeskills.eshop.entities.Order;
import by.teachmeskills.eshop.repositories.OrderRepository;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static by.teachmeskills.eshop.utils.EshopConstants.ORDERS_PER_PAGE;

@Transactional
@Repository
public class OrderRepositoryImpl implements OrderRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order create(Order entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public long countAllOrdersByUser(int id) {
        int pageSize = 5;
        Query query = entityManager.createQuery("select count(o) from Order o where o.user.id=:id");
        query.setParameter("id", id);
        long resultQuery = (Long) query.getSingleResult();
        if (resultQuery % pageSize != 0) {
            return resultQuery / pageSize + 1;
        }
        return resultQuery / pageSize;
    }

    @Override
    public Order getOrderById(int id) {
        return entityManager.find(Order.class, id);
    }

    @Override
    public List<Order> getAllOrdersByUserId(int userId, int pageReq) {
        int pageSize = ORDERS_PER_PAGE;
        int firstResult;
        if (pageReq > 1) {
            firstResult = (pageReq - 1) * pageSize;
        } else {
            firstResult = 0;
        }
        Query query=entityManager.createQuery("select o from Order o where o.user.id=:id order by o.id desc");
        query.setParameter("id", userId);
        query.setFirstResult(firstResult);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public List<Order> read() {
        return entityManager.createQuery("select o from Order o").getResultList();
    }

    //method should be updated
    @Override
    public Order update(Order entity) {
        Order order = entityManager.find(Order.class, entity.getId());
        order.setPriceOrder(entity.getPriceOrder());
        order.setProducts(entity.getProducts());
        order.setUser(entity.getUser());
        order.setDateCreation(entity.getDateCreation());
        entityManager.persist(order);
        return order;
    }

    //method should be updated
    @Override
    public void delete(int id) {
        Order order = entityManager.find(Order.class, id);
        entityManager.remove(order);
    }
}
package by.teachmeskills.eshop.repositories;

import by.teachmeskills.eshop.entities.Order;
import by.teachmeskills.eshop.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order getOrderById(int id);

    Page<Order> getOrdersByUserId(int id, Pageable pageable);

    void deleteOrderById(int id);
}
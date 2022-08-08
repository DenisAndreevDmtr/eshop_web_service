package by.teachmeskills.eshop.services;

import by.teachmeskills.eshop.entities.Order;

import java.io.Writer;

public interface OrderService extends BaseService<Order> {
    void saveOrderToCsvFile(Writer writer, int id);
}

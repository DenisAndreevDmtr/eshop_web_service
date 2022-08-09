package by.teachmeskills.eshop.services.impl;

import by.teachmeskills.eshop.entities.Order;
import by.teachmeskills.eshop.repositories.OrderRepository;
import by.teachmeskills.eshop.services.OrderService;
import by.teachmeskills.eshop.utils.CsvUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.Writer;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CsvUtil csvUtil;

    public OrderServiceImpl(OrderRepository orderRepository, CsvUtil csvUtil) {
        this.orderRepository = orderRepository;
        this.csvUtil = csvUtil;
    }

    @Override
    public Order create(Order entity) {
        return orderRepository.save(entity);
    }

    @Override
    public List<Order> read() {
        return orderRepository.findAll();
    }

    @Override
    public Order update(Order entity) {
        return orderRepository.save(entity);
    }

    @Override
    public void delete(int id) {
        orderRepository.deleteOrderById(id);
    }

    @Override
    public void saveOrderToCsvFile(Writer writer, int id) {
        Order order = orderRepository.getOrderById(id);
        csvUtil.saveProductsToCsvFile(writer, order);
    }
}
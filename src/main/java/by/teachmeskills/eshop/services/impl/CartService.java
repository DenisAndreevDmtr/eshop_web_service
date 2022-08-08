package by.teachmeskills.eshop.services.impl;

import by.teachmeskills.eshop.dto.CartDto;
import by.teachmeskills.eshop.dto.OrderDto;
import by.teachmeskills.eshop.dto.converters.CartConverter;
import by.teachmeskills.eshop.dto.converters.OrderConverter;
import by.teachmeskills.eshop.entities.Order;
import by.teachmeskills.eshop.entities.Product;
import by.teachmeskills.eshop.entities.User;
import by.teachmeskills.eshop.model.Cart;
import by.teachmeskills.eshop.repositories.OrderRepository;
import by.teachmeskills.eshop.repositories.ProductRepository;
import by.teachmeskills.eshop.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Service
public class CartService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final CartConverter cartConverter;
    private final OrderConverter orderConverter;
    private final UserRepository userRepository;

    public CartService(ProductRepository productRepository, OrderRepository orderRepository, CartConverter cartConverter, OrderConverter orderConverter, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.cartConverter = cartConverter;
        this.orderConverter = orderConverter;
        this.userRepository = userRepository;
    }

    public CartDto addProductToCart(int productId, int productQuantity, CartDto cartDto) {
        try {
            Cart cart = cartConverter.fromDto(cartDto);
            Product product = productRepository.getProductById(productId);
            for (int i = 0; i < productQuantity; i++) {
                cart.addProduct(product);
            }
            return cartConverter.toDto(cart);
        } catch (Exception e) {
            return null;
        }
    }

    public CartDto removeProductFromCart(int productId, int productQuantity, CartDto cartDto) {
        try {
            Cart cart = cartConverter.fromDto(cartDto);
            Product product = productRepository.getProductById(productId);
            for (int i = 0; i < productQuantity; i++) {
                cart.removeProduct(product);
            }
            return cartConverter.toDto(cart);
        } catch (Exception e) {
            return null;
        }
    }

    public OrderDto checkOut(CartDto cartDto) {
        try {
            Cart cart = cartConverter.fromDto(cartDto);
            LocalDate date = LocalDate.now();
            BigDecimal priceOrder = cart.getTotalPrice();
            Map<Product, Integer> products = cart.getProductsAndQuantity();
            User user = userRepository.findById(cart.getUserId()).get();
            Order order = Order.builder().priceOrder(priceOrder).
                    dateCreation(date).
                    user(user).
                    products(products).
                    build();
            Order createdOrder = orderRepository.save(order);
            return orderConverter.toDto(createdOrder);
        } catch (Exception e) {
            return null;
        }
    }
}
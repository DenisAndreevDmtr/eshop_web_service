package by.teachmeskills.eshop.services.impl;

import by.teachmeskills.eshop.model.Cart;
import by.teachmeskills.eshop.entities.Order;
import by.teachmeskills.eshop.entities.Product;
import by.teachmeskills.eshop.entities.User;
import by.teachmeskills.eshop.repositories.OrderRepository;
import by.teachmeskills.eshop.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static by.teachmeskills.eshop.utils.EshopConstants.SHOPPING_CART;
import static by.teachmeskills.eshop.utils.PagesPathEnum.CART_PAGE;
import static by.teachmeskills.eshop.utils.PagesPathEnum.ORDER_PAGE;
import static by.teachmeskills.eshop.utils.PagesPathEnum.PRODUCT_PAGE;
import static by.teachmeskills.eshop.utils.RequestParamsEnum.ID_ORDER;
import static by.teachmeskills.eshop.utils.RequestParamsEnum.PRICE_ORDER;
import static by.teachmeskills.eshop.utils.RequestParamsEnum.PRODUCT;

@Service
public class CartService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public CartService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public ModelAndView addProductToCart(int productId, Cart shopCart) {
        ModelMap modelParams = new ModelMap();
        Product product = productRepository.getProductById(productId);
        shopCart.addProduct(product);
        modelParams.addAttribute(PRODUCT.getValue(), product);
        modelParams.addAttribute(SHOPPING_CART, shopCart);
        return new ModelAndView(PRODUCT_PAGE.getPath(), modelParams);
    }

    public ModelAndView removeProductFromCart(int productId, Cart shopCart) {
        ModelMap modelParams = new ModelMap();
        Product product = productRepository.getProductById(productId);
        shopCart.removeProduct(product);
        modelParams.addAttribute(SHOPPING_CART, shopCart);
        return new ModelAndView(CART_PAGE.getPath(), modelParams);
    }

    public ModelAndView increaseProductQuantity(int productId, Cart shopCart) {
        ModelMap modelParams = new ModelMap();
        Product product = productRepository.getProductById(productId);
        shopCart.addProduct(product);
        modelParams.addAttribute(PRODUCT.getValue(), product);
        modelParams.addAttribute(SHOPPING_CART, shopCart);
        return new ModelAndView(CART_PAGE.getPath(), modelParams);
    }

    public ModelAndView decreaseProductQuantity(int productId, Cart shopCart) {
        ModelMap modelParams = new ModelMap();
        Product product = productRepository.getProductById(productId);
        shopCart.decreaseQuantity(product);
        modelParams.addAttribute(PRODUCT.getValue(), product);
        modelParams.addAttribute(SHOPPING_CART, shopCart);
        return new ModelAndView(CART_PAGE.getPath(), modelParams);
    }

    public ModelAndView checkOut(Cart shopCart, User user) {
        ModelMap model = new ModelMap();
        Map<Product, Integer> products = shopCart.getProductsAndQuantity();
        BigDecimal priceOrder = shopCart.getTotalPrice();
        LocalDate date = LocalDate.now();
        Order order = Order.builder().priceOrder(priceOrder).
                dateCreation(date).
                user(user).
                products(products).
                build();
        Order createdOrder = orderRepository.create(order);
        model.addAttribute(PRICE_ORDER.getValue(), shopCart.getTotalPrice());
        model.addAttribute(ID_ORDER.getValue(), createdOrder.getId());
        shopCart.clear();
        return new ModelAndView(ORDER_PAGE.getPath(), model);
    }
}
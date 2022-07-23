package by.teachmeskills.eshop.model;

import by.teachmeskills.eshop.entities.BaseEntity;
import by.teachmeskills.eshop.entities.Product;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
public class Cart extends BaseEntity {

    private Map<Product, Integer> products;
    private BigDecimal totalPrice = new BigDecimal(0);

    public Cart() {
        this.products = new HashMap<>();
    }

    public void addProduct(Product product) {
        if (products.containsKey(product)) {
            Integer quantity = products.get(product);
            quantity = quantity + 1;
            products.put(product, quantity);
        } else {
            Integer quantity = 1;
            products.put(product, quantity);
        }
        totalPrice = totalPrice.add(product.getPrice());
    }

    public void removeProduct(Product product) {
        BigDecimal sumDecrease = product.getPrice().multiply(BigDecimal.valueOf(products.get(product)));
        totalPrice = totalPrice.subtract(sumDecrease);
        products.remove(product);
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products.keySet());
    }

    public int getQuantity(Product product) {
        return products.get(product);
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void clear() {
        products.clear();
    }

    public void decreaseQuantity(Product product) {
        int quantity = products.get(product);
        if (quantity > 1) {
            quantity = quantity - 1;
            products.put(product, quantity);
        } else {
            products.remove(product);
        }
        totalPrice = totalPrice.subtract(product.getPrice());
    }

    public Map<Product, Integer> getProductsAndQuantity() {
        return products;
    }
}
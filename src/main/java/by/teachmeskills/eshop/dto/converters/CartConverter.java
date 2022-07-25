package by.teachmeskills.eshop.dto.converters;

import by.teachmeskills.eshop.dto.CartDto;
import by.teachmeskills.eshop.dto.ProductDto;
import by.teachmeskills.eshop.model.Cart;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CartConverter {
    private final ProductConverter productConverter;

    public CartConverter(ProductConverter productConverter) {
        this.productConverter = productConverter;
    }

    public CartDto toDto(Cart cart) {
        return Optional.ofNullable(cart).map(c -> CartDto.builder()
                        .userId(c.getUserId())
                        .totalPrice(c.getTotalPrice())
                        .products(c.getProductsAndQuantity().keySet().stream().collect(Collectors.toMap(
                                productConverter::toDto,
                                c.getProductsAndQuantity()::get)))
                        .build())
                .orElse(null);
    }

    public Cart fromDto(CartDto cartDto) {

        return Optional.ofNullable(cartDto).map(cd -> Cart.builder()
                        .userId(cd.getUserId())
                        .totalPrice(cd.getTotalPrice())
                        .products(cd.getProducts().keySet().stream().collect(Collectors.toMap(
                                e -> productConverter.fromDto((ProductDto) e),
                                cd.getProducts()::get)))
                        .build())
                .orElse(null);
    }
}
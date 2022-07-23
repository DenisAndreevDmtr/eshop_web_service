package by.teachmeskills.eshop.dto.converters;

import by.teachmeskills.eshop.dto.OrderDto;
import by.teachmeskills.eshop.dto.ProductDto;
import by.teachmeskills.eshop.entities.Order;
import by.teachmeskills.eshop.entities.Product;
import by.teachmeskills.eshop.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OrderConverter {
    private final ProductConverter productConverter;
    private final UserRepository userRepository;

    public OrderConverter(ProductConverter productConverter, UserRepository userRepository) {
        this.productConverter = productConverter;
        this.userRepository = userRepository;
    }

    public OrderDto toDto(Order order) {
        Map<Product, Integer> products = order.getProducts();
        Map<ProductDto, Integer> productsDto = products.entrySet().stream().collect(Collectors.toMap(entry -> productConverter.toDto(entry.getKey()), Map.Entry::getValue));
        return Optional.ofNullable(order).map(o -> OrderDto.builder()
                        .id(o.getId())
                        .price(o.getPriceOrder())
                        .date(o.getDateCreation())
                        .userId(o.getUser().getId())
                        .products(productsDto)
                        .build())
                .orElse(null);
    }

    public Order fromDto(OrderDto orderDto) {
        List<ProductDto> productsDto = orderDto.getProductsDto();
        Map<ProductDto, Integer> productsDtoMap = new HashMap<>();
        for (int i = 0; i < productsDto.size(); i++) {
            if (productsDtoMap.containsKey(productsDto.get(i))) {
                int oldCount = productsDtoMap.get(productsDto.get(i));
                productsDtoMap.replace(productsDto.get(i), oldCount, oldCount + 1);
            } else {
                productsDtoMap.put(productsDto.get(i), 1);
            }
        }
        Map<Product, Integer> productsMap = productsDtoMap.entrySet().stream().collect(Collectors.toMap(entry -> productConverter.fromDto(entry.getKey()), Map.Entry::getValue));
        return Optional.ofNullable(orderDto).map(o -> Order.builder()
                        .priceOrder(orderDto.getPrice())
                        .dateCreation(orderDto.getDate())
                        .user(userRepository.getUserById(orderDto.getUserId()))
                        .products(productsMap)
                        .build())
                .orElse(null);
    }
}
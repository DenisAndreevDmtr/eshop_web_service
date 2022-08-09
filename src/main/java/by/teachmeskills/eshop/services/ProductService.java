package by.teachmeskills.eshop.services;

import by.teachmeskills.eshop.dto.ProductDto;
import by.teachmeskills.eshop.entities.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProductService extends BaseService<Product> {

    ProductDto getProductData(int id);


    ProductDto createFromDto(ProductDto productDto);

    ProductDto updateFromDto(ProductDto productDto);

    void deleteFromDto(ProductDto productDto);

    Page<Product> getAllProductsByCategory(int categoryId, int pageNumber, int pageSize);

    List<ProductDto> searchProducts(Optional<Integer> oPriceFrom, Optional<Integer> oPriceTo, String searchParametr, String searchCategory, int pageNumber, int pageSize);
}
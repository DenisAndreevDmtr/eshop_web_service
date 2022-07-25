package by.teachmeskills.eshop.services;

import by.teachmeskills.eshop.dto.ProductDto;
import by.teachmeskills.eshop.entities.Product;

import java.util.List;

public interface ProductService extends BaseService<Product> {
    List<Product> getAllProductsByCategory(int categoryId);

    ProductDto getProductData(int id);

    List<ProductDto> getSearchResult(String searchParametr);

    List<Product> getAllProductsByCategoryPaging(int categoryId, int number);

    public long countAllProductsByCategory(int categoryId);

    ProductDto createFromDto(ProductDto productDto);

    ProductDto updateFromDto(ProductDto productDto);

    void deleteFromDto(ProductDto productDto);
}
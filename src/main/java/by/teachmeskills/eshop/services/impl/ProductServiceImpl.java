package by.teachmeskills.eshop.services.impl;

import by.teachmeskills.eshop.dto.ProductDto;
import by.teachmeskills.eshop.dto.converters.ProductConverter;
import by.teachmeskills.eshop.entities.Product;
import by.teachmeskills.eshop.repositories.CategoryRepository;
import by.teachmeskills.eshop.repositories.ProductRepository;
import by.teachmeskills.eshop.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductConverter productConverter;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ProductConverter productConverter) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productConverter = productConverter;
    }

    @Override
    public Product create(Product entity) {
        return productRepository.create(entity);
    }

    @Override
    public ProductDto createFromDto(ProductDto productDto) {
        try {
            Product product = productConverter.fromDto(productDto);
            product = productRepository.create(product);
            return productConverter.toDto(product);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Product> read() {
        return productRepository.read();
    }

    @Override
    public Product update(Product entity) {
        return productRepository.update(entity);
    }

    @Override
    public ProductDto updateFromDto(ProductDto productDto) {
        try {
            Product product = productConverter.fromDto(productDto);
            product = productRepository.update(product);
            return productConverter.toDto(product);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void delete(int id) {
        productRepository.delete(id);
    }

    @Override
    public void deleteFromDto(ProductDto productDto) {
        productRepository.delete(productDto.getId());
    }

    @Override
    public List<Product> getAllProductsByCategory(int categoryId) {
        return productRepository.getAllProductsByCategoryId(categoryId);
    }

    @Override
    public ProductDto getProductData(int id) {
        return productConverter.toDto(productRepository.getProductById(id));
    }

    @Override
    public List<ProductDto> getSearchResult(String searchParametr) {
        List<Product> productListResult = productRepository.getListProductsByNameOrDesc(searchParametr);
        List<Product> requestProducts = new ArrayList<>();
        requestProducts.addAll(productListResult.stream().filter(x -> x.getName().contains(searchParametr)).collect(Collectors.toList()));
        requestProducts.addAll(productListResult.stream().filter(x -> !x.getName().contains(searchParametr)).collect(Collectors.toList()));
        return requestProducts.stream().map(productConverter::toDto).toList();
    }

    @Override
    public List<Product> getAllProductsByCategoryPaging(int categoryId, int number) {
        return productRepository.getAllProductsByCategoryIdPaging(categoryId, number);
    }

    @Override
    public long countAllProductsByCategory(int categoryId) {
        return productRepository.countAllProductsByCategory(categoryId);
    }
}
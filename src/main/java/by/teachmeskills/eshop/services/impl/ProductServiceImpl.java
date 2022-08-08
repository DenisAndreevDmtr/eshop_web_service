package by.teachmeskills.eshop.services.impl;

import by.teachmeskills.eshop.dto.ProductDto;
import by.teachmeskills.eshop.dto.SearchParamsDto;
import by.teachmeskills.eshop.dto.converters.ProductConverter;
import by.teachmeskills.eshop.entities.Category;
import by.teachmeskills.eshop.entities.Product;
import by.teachmeskills.eshop.repositories.CategoryRepository;
import by.teachmeskills.eshop.repositories.ProductRepository;
import by.teachmeskills.eshop.repositories.ProductSearchSpecification;
import by.teachmeskills.eshop.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return productRepository.save(entity);
    }

    @Override
    public ProductDto createFromDto(ProductDto productDto) {
        try {
            Product product = productConverter.fromDto(productDto);
            product = productRepository.save(product);
            return productConverter.toDto(product);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Product> read() {
        return productRepository.findAll();
    }

    @Override
    public Product update(Product entity) {
        return productRepository.save(entity);
    }

    @Override
    public ProductDto updateFromDto(ProductDto productDto) {
        try {
            Product product = productConverter.fromDto(productDto);
            product = productRepository.save(product);
            return productConverter.toDto(product);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void delete(int id) {
        productRepository.deleteProductById(id);
    }

    @Override
    public void deleteFromDto(ProductDto productDto) {
        productRepository.deleteProductById(productDto.getId());
    }

    @Override
    public Page<Product> getAllProductsByCategory(int categoryId, int pageNumber, int pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());
        return productRepository.findAllByCategoryId(categoryId, paging);
    }

    @Override
    public ProductDto getProductData(int id) {
        return productConverter.toDto(productRepository.getProductById(id));
    }

    @Override
    public List<ProductDto> searchProducts(Optional<Integer> oPriceFrom, Optional<Integer> oPriceTo, String searchParametr, String searchCategory, int pageNumber, int pageSize) {
        int priceFrom = getIntValue(oPriceFrom);
        int priceTo = getIntValue(oPriceTo);
        SearchParamsDto searchParamsDto = SearchParamsDto.builder().
                searchParametr(searchParametr).priceFrom(priceFrom).priceTo(priceTo).searchCategory(searchCategory).build();
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());
        ProductSearchSpecification productSearchSpecification = new ProductSearchSpecification(searchParamsDto);
        List<Product> searchResult = productRepository.findAll(productSearchSpecification, paging).getContent();
        return searchResult.stream().map(productConverter::toDto).toList();
    }

    private int getIntValue(Optional<Integer> optionalInteger) {
        int result;
        if (optionalInteger.isEmpty()) {
            result = 0;
        } else {
            result = optionalInteger.get();
        }
        return result;
    }
}
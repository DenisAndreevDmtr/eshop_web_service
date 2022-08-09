package by.teachmeskills.eshop.services.impl;

import by.teachmeskills.eshop.dto.CategoryDto;
import by.teachmeskills.eshop.dto.converters.CategoryConverter;
import by.teachmeskills.eshop.entities.Category;
import by.teachmeskills.eshop.entities.Product;
import by.teachmeskills.eshop.repositories.CategoryRepository;
import by.teachmeskills.eshop.services.CategoryService;
import by.teachmeskills.eshop.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductService productService;
    private final CategoryConverter categoryConverter;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductService productService, CategoryConverter categoryConverter) {
        this.categoryRepository = categoryRepository;
        this.productService = productService;
        this.categoryConverter = categoryConverter;
    }

    @Override
    public CategoryDto createCategoryFromDto(CategoryDto categoryDto) {
        try {
            Category category = categoryConverter.fromDto(categoryDto);
            category = categoryRepository.save(category);
            return categoryConverter.toDto(category);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Category create(Category entity) {
        Category category = categoryRepository.save(entity);
        return category;
    }

    @Override
    public List<Category> read() {
        return categoryRepository.findAll();
    }

    @Override
    public List<CategoryDto> readDto() {
        return categoryRepository.findAll().stream().map(categoryConverter::toDto).toList();
    }

    @Override
    public Category update(Category entity) {
        Category category = categoryRepository.save(entity);
        return category;
    }

    @Override
    public CategoryDto updateCategoryFromDto(CategoryDto categoryDto) {
        try {
            Category category = categoryConverter.fromDto(categoryDto);
            category = categoryRepository.save(category);
            return categoryConverter.toDto(category);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void delete(int id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public void deleteCategoryFromDto(CategoryDto categoryDto) {
        categoryRepository.deleteById(categoryDto.getId());
    }

    @Override
    public List<CategoryDto> getHomePageData() {
        return categoryRepository.findAll().stream().map(categoryConverter::toDto).toList();
    }

    @Override
    public List<CategoryDto> getSearchPageData() {
        return categoryRepository.findAll().stream().map(categoryConverter::toDto).toList();
    }

    @Override
    public CategoryDto getCategoryData(int id, int pageNumber, int pageSize) {
        Category category = categoryRepository.getCategoryById(id);
        if (Optional.ofNullable(category).isPresent()) {
            Page<Product> products = productService.getAllProductsByCategory(category.getId(), pageNumber, pageSize);
            category.setProductList(products.getContent());
        }
        return categoryConverter.toDto(category);
    }
}
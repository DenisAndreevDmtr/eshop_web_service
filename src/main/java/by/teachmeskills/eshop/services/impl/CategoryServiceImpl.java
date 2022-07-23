package by.teachmeskills.eshop.services.impl;

import by.teachmeskills.eshop.dto.CategoryDto;
import by.teachmeskills.eshop.dto.converters.CategoryConverter;
import by.teachmeskills.eshop.entities.Category;
import by.teachmeskills.eshop.entities.Product;
import by.teachmeskills.eshop.repositories.CategoryRepository;
import by.teachmeskills.eshop.services.CategoryService;
import by.teachmeskills.eshop.services.ProductService;
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
            category = categoryRepository.create(category);
            return categoryConverter.toDto(category);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Category create(Category entity) {
        Category category = categoryRepository.create(entity);
        return category;
    }

    @Override
    public List<Category> read() {
        return categoryRepository.read();
    }

    @Override
    public List<CategoryDto> readDto() {
        return categoryRepository.getAllCategories().stream().map(categoryConverter::toDto).toList();
    }

    @Override
    public Category update(Category entity) {
        Category category = categoryRepository.update(entity);
        return category;
    }

    @Override
    public CategoryDto updateCategoryFromDto(CategoryDto categoryDto) {
        try {
            Category category = categoryConverter.fromDto(categoryDto);
            category = categoryRepository.update(category);
            return categoryConverter.toDto(category);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void delete(int id) {
        categoryRepository.delete(id);
    }

    @Override
    public void deleteCategoryFromDto(CategoryDto categoryDto) {
        categoryRepository.delete(categoryDto.getId());
    }

    @Override
    public List<CategoryDto> getHomePageData() {
        return categoryRepository.getAllCategories().stream().map(categoryConverter::toDto).toList();
    }

    @Override
    public List<CategoryDto> getSearchPageData() {
        return categoryRepository.getAllCategories().stream().map(categoryConverter::toDto).toList();
    }

    @Override
    public CategoryDto getCategoryDataPaging(int id, int number) {
        Category category = categoryRepository.getCategoryById(id);
        if (Optional.ofNullable(category).isPresent()) {
            List<Product> products = productService.getAllProductsByCategoryPaging(category.getId(), number);
            category.setProductList(products);
        }
        return categoryConverter.toDto(category);
    }
}
package by.teachmeskills.eshop.dto.converters;

import by.teachmeskills.eshop.dto.CategoryDto;
import by.teachmeskills.eshop.entities.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CategoryConverter {
    private final ProductConverter productConverter;

    public CategoryConverter(ProductConverter productConverter) {
        this.productConverter = productConverter;
    }

    public CategoryDto toDto(Category category) {
        System.out.println(category.getId());
        return Optional.ofNullable(category).map(c -> CategoryDto.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .imagePath(c.getImagePath())
                        .rating(c.getRating())
                        .products(Optional.ofNullable(c.getProductList()).map(products -> products.stream().map(productConverter::toDto).toList()).orElse(List.of()))
                        .build())
                .orElse(null);
    }

    public Category fromDto(CategoryDto categoryDto) {
        return Optional.ofNullable(categoryDto).map(cd -> Category.builder()
                        .id(categoryDto.getId())
                        .name(categoryDto.getName())
                        .rating(categoryDto.getRating())
                        .imagePath(categoryDto.getImagePath())
                        .build())
                .orElse(null);
    }
}
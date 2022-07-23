package by.teachmeskills.eshop.services;

import by.teachmeskills.eshop.dto.CategoryDto;
import by.teachmeskills.eshop.entities.Category;


import java.util.List;

public interface CategoryService extends BaseService<Category> {

    List<CategoryDto> getHomePageData();

    List<CategoryDto> getSearchPageData();

    CategoryDto getCategoryDataPaging(int id, int number);

    CategoryDto createCategoryFromDto(CategoryDto categoryDto);

    CategoryDto updateCategoryFromDto(CategoryDto categoryDto);

    void deleteCategoryFromDto(CategoryDto categoryDto);

    List<CategoryDto> readDto();
}
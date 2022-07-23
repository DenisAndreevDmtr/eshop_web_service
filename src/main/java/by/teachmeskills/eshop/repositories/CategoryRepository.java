package by.teachmeskills.eshop.repositories;

import by.teachmeskills.eshop.entities.Category;

import java.util.List;

public interface CategoryRepository extends BaseRepository<Category> {
    List<Category> getAllCategories();

    String getCategoryNameByID(int id);

    Category getCategoryById(int id);
}
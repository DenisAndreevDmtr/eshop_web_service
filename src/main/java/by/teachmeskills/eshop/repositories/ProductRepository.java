package by.teachmeskills.eshop.repositories;

import by.teachmeskills.eshop.entities.Product;

import java.util.List;
import java.util.Map;

public interface ProductRepository extends BaseRepository<Product> {

    List<Product> getAllProductsByCategoryId(int id);

    Product getProductById(int id);

    List<Product> getListProductsByNameOrDesc(String param);

    Map<Product, Integer> getAllProductsByOrderId(int id);

    long countAllProductsByCategory(int categoryId);

    List<Product> getAllProductsByCategoryIdPaging(int categoryId, int pageReq);

    long countProductsByNameOrDesc(String param);
}
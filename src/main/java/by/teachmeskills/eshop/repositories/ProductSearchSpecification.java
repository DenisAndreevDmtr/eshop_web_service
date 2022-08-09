package by.teachmeskills.eshop.repositories;

import by.teachmeskills.eshop.dto.SearchParamsDto;
import by.teachmeskills.eshop.entities.Category;
import by.teachmeskills.eshop.entities.Product;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.teachmeskills.eshop.utils.EshopConstants.ALL_CATEGORIES;

public class ProductSearchSpecification implements Specification<Product> {
    private SearchParamsDto searchParamsDto;

    public ProductSearchSpecification(SearchParamsDto searchParamsDto) {
        this.searchParamsDto = searchParamsDto;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (Optional.ofNullable(searchParamsDto.getSearchParametr()).isPresent() && !searchParamsDto.getSearchParametr().isBlank()) {
            predicates.add(criteriaBuilder
                    .or(criteriaBuilder.like(root.get("name"), "%" + searchParamsDto.getSearchParametr() + "%"),
                            criteriaBuilder.like(root.get("description"), "%" + searchParamsDto.getSearchParametr() + "%")));
        }

        if (searchParamsDto.getPriceFrom() > 0) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), searchParamsDto.getPriceFrom()));
        }

        if (searchParamsDto.getPriceTo() > 0) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), searchParamsDto.getPriceTo()));
        }

        if (Optional.ofNullable(searchParamsDto.getSearchCategory()).isPresent()
                && !searchParamsDto.getSearchCategory().equals(ALL_CATEGORIES)) {
            Join<Product, Category> productCategoryJoin = root.join("category");
            predicates.add(criteriaBuilder.and(criteriaBuilder.equal(productCategoryJoin.get("name"),
                    searchParamsDto.getSearchCategory())));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
package by.teachmeskills.eshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchParamsDto {
    private String searchParametr;
    private int priceTo;
    private int priceFrom;
    private String searchCategory;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchParamsDto that = (SearchParamsDto) o;
        return priceTo == that.priceTo && priceFrom == that.priceFrom && Objects.equals(searchParametr, that.searchParametr) && Objects.equals(searchCategory, that.searchCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(searchParametr, priceTo, priceFrom, searchCategory);
    }
}
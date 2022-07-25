package by.teachmeskills.eshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Objects;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    @Min(value = 1, message = "Min values is 1")
    private int id;
    private String name;
    private String imagePath;
    private String description;
    private BigDecimal price;
    private int categoryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return id == that.id && categoryId == that.categoryId && Objects.equals(name, that.name) && Objects.equals(imagePath, that.imagePath) && Objects.equals(description, that.description) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, imagePath, description, price, categoryId);
    }
}
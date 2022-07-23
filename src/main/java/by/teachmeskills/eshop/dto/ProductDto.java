package by.teachmeskills.eshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

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
}
package by.teachmeskills.eshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    @Min(value = 1, message = "Min values is 1")
    private int id;
    private String name;
    private int rating;
    private String imagePath;
    private List<ProductDto> products;
}
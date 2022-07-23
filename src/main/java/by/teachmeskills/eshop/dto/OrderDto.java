package by.teachmeskills.eshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    @Min(value = 1, message = "Min values is 1")
    private int id;
    private BigDecimal price;
    private LocalDate date;
    private int userId;
    private Map<ProductDto, Integer> products;

    public List<ProductDto> getProductsDto() {
        List<ProductDto> resultList = new ArrayList<>();
        for (Map.Entry entry : products.entrySet()) {
            Integer i = (Integer) entry.getValue();
            for (int j = 0; j < i; j++) {
                resultList.add((ProductDto) entry.getKey());
            }
        }
        return resultList;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getUserId() {
        return userId;
    }
}
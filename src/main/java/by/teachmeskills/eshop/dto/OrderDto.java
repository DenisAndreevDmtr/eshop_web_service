package by.teachmeskills.eshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDto {
    @Min(value = 1, message = "Min values is 1")
    private int id;
    private BigDecimal price;
    private LocalDate date;
    private int userId;
    private Map<Object, Integer> products;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return id == orderDto.id && userId == orderDto.userId && Objects.equals(price, orderDto.price) && Objects.equals(date, orderDto.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, date, userId);
    }
}
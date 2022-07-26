package by.teachmeskills.eshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private Map<Object, Integer> products;
    private BigDecimal totalPrice;
    private int userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartDto cartDto = (CartDto) o;
        return userId == cartDto.userId && Objects.equals(totalPrice, cartDto.totalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalPrice, userId);
    }
}

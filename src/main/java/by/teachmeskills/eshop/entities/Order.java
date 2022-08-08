package by.teachmeskills.eshop.entities;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@SuperBuilder
@Data
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Column(name = "price")
    @CsvBindByName(column = "priceOrder")
    private BigDecimal priceOrder;
    @Column(name = "date_Order")
    @CsvBindByName(column = "dateCreation")
    private LocalDate dateCreation;
    @ManyToOne
    private User user;
    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "product_quantity", nullable = false)
    @CsvBindByName(column = "products")
    private Map<Product, Integer> products = new HashMap<>();

    public Map<Product, Integer> getProducts() {
        return Collections.unmodifiableMap(products);
    }

    public void addProducts(Product product, int quantity) {
        products.merge(product, quantity, Integer::sum);
    }

    public void removeItem(Product product) {
        products.computeIfPresent(product, (k, v) -> v > 1 ? v - 1 : null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return Objects.equals(priceOrder, order.priceOrder) && Objects.equals(dateCreation, order.dateCreation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), priceOrder, dateCreation);
    }
}
package by.teachmeskills.eshop.entities;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString

@SuperBuilder
@Entity
@NoArgsConstructor
@Table(name = "category")
public class Category extends BaseEntity {
    @Column(name = "name")
    @CsvBindByName(column = "name")
    private String name;
    @Column(name = "rating")
    @CsvBindByName(column = "rating")
    private int rating;
    @Column(name = "image_Path")
    @CsvBindByName(column = "image_path")
    private String imagePath;
    @OneToMany(mappedBy = "category", orphanRemoval = true, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Product> productList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Category category = (Category) o;
        return rating == category.rating && Objects.equals(name, category.name) && Objects.equals(imagePath, category.imagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, rating, imagePath);
    }
}
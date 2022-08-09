package by.teachmeskills.eshop.entities;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    @Column(name = "login")
    @Pattern(regexp = "\\S+", message = "Spaces are not allowed")
    @CsvBindByName(column = "login")
    private String login;
    @Pattern(regexp = "\\S+", message = "Spaces are not allowed")
    @Column(name = "password")
    @CsvBindByName(column = "password")
    private String password;
    @Column(name = "name")
    @CsvBindByName(column = "name")
    private String name;
    @CsvBindByName(column = "surname")
    @Column(name = "surname")
    private String surname;
    @CsvBindByName(column = "birth_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birth_date")
    private LocalDate dateBorn;
    @CsvBindByName(column = "email")
    @Column(name = "email")
    private String eMail;
    @CsvBindByName(column = "balance")
    @Column(name = "balance")
    private BigDecimal balance;
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Order> order;
    @CsvBindByName(column = "role_name")
    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;


    public User(int id, String login, String password, String name, String surname, LocalDate dateBorn, String eMail, BigDecimal balance, Role role) {
        super(id);
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.dateBorn = dateBorn;
        this.eMail = eMail;
        this.balance = balance;
        this.role = role;
    }

    public boolean checkFieldsNotNull(User user) {
        boolean flag = true;
        if (user.getLogin().isEmpty() ||
                user.getPassword().isEmpty() ||
                user.getName().isEmpty() ||
                user.getSurname().isEmpty() ||
                user.getEMail().isEmpty() ||
                user.getDateBorn() == null) {
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(dateBorn, user.dateBorn) && Objects.equals(eMail, user.eMail) && Objects.equals(balance, user.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), login, password, name, surname, dateBorn, eMail, balance);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dateBorn=" + dateBorn +
                ", eMail='" + eMail + '\'' +
                ", balance=" + balance + '\'' +
                ", role=" + role.getName() +
                '}';
    }
}
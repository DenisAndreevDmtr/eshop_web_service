package by.teachmeskills.eshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@ToString
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @Min(value = 1, message = "Min values is 1")
    private int id;
    private String login;
    private String password;
    private String name;
    private String surname;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateBorn;
    private String eMail;
    private BigDecimal balance;
    private String roleName;
    @ToString.Exclude
    private List<OrderDto> orders;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return id == userDto.id && Objects.equals(login, userDto.login) && Objects.equals(password, userDto.password) && Objects.equals(name, userDto.name) && Objects.equals(surname, userDto.surname) && Objects.equals(dateBorn, userDto.dateBorn) && Objects.equals(eMail, userDto.eMail) && Objects.equals(balance, userDto.balance) && Objects.equals(roleName, userDto.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, name, surname, dateBorn, eMail, balance, roleName);
    }
}
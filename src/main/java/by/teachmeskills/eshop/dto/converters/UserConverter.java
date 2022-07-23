package by.teachmeskills.eshop.dto.converters;

import by.teachmeskills.eshop.dto.UserDto;
import by.teachmeskills.eshop.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserConverter {
    private final OrderConverter orderConverter;

    public UserConverter(OrderConverter orderConverter) {
        this.orderConverter = orderConverter;
    }

    public UserDto toDto(User user) {
        return Optional.ofNullable(user).map(u -> UserDto.builder()
                        .id(user.getId())
                        .login(user.getLogin())
                        .password(user.getPassword())
                        .name(user.getName())
                        .surname(user.getSurname())
                        .dateBorn(user.getDateBorn())
                        .eMail(user.getEMail())
                        .balance(user.getBalance())
                        .orders(Optional.ofNullable(user.getOrder()).map(products -> products.stream().map(orderConverter::toDto).toList()).orElse(List.of()))
                        .build())
                .orElse(null);
    }

    public User fromDto(UserDto userDto) {
        return Optional.ofNullable(userDto).map(u -> User.builder()
                        .login(userDto.getLogin())
                        .password(userDto.getPassword())
                        .name(userDto.getName())
                        .surname(userDto.getSurname())
                        .dateBorn(userDto.getDateBorn())
                        .eMail(userDto.getEMail())
                        .balance(userDto.getBalance())
                        .order(Optional.ofNullable(userDto.getOrders()).map(products -> products.stream().map(orderConverter::fromDto).toList()).orElse(List.of()))
                        .build())
                .orElse(null);
    }
}
package by.teachmeskills.eshop.dto.converters;

import by.teachmeskills.eshop.dto.UserDto;
import by.teachmeskills.eshop.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserConverter {
    private final OrderConverter orderConverter;
    private final RoleConverter roleConverter;

    public UserConverter(OrderConverter orderConverter, RoleConverter roleConverter) {
        this.orderConverter = orderConverter;
        this.roleConverter = roleConverter;
    }

    public UserDto toDto(User user) {
        return Optional.ofNullable(user).map(u -> UserDto.builder()
                        .id(u.getId())
                        .login(u.getLogin())
                        .password(u.getPassword())
                        .name(u.getName())
                        .surname(u.getSurname())
                        .dateBorn(u.getDateBorn())
                        .eMail(u.getEMail())
                        .balance(u.getBalance())
                        .roleName(u.getRole().getName())
                        .orders(Optional.ofNullable(u.getOrder()).map(products -> products.stream().map(orderConverter::toDto).toList()).orElse(List.of()))
                        .build())
                .orElse(null);
    }

    public User fromDto(UserDto userDto) {
        return Optional.ofNullable(userDto).map(u -> User.builder()
                        .login(u.getLogin())
                        .password(u.getPassword())
                        .name(u.getName())
                        .surname(u.getSurname())
                        .dateBorn(u.getDateBorn())
                        .eMail(u.getEMail())
                        .balance(u.getBalance())
                        .role(roleConverter.fromDto(u.getRoleName()))
                        .order(Optional.ofNullable(u.getOrders()).map(products -> products.stream().map(orderConverter::fromDto).toList()).orElse(List.of()))
                        .build())
                .orElse(null);
    }
}
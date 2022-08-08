package by.teachmeskills.eshop.services;

import by.teachmeskills.eshop.dto.UserDto;
import by.teachmeskills.eshop.entities.User;

import java.util.Optional;

public interface UserService extends BaseService<User> {

    UserDto registerNewUser(UserDto userForLoginDto);

    UserDto getDataAboutLoggedInUser(int userId, int pageNumber, int pageSize);

    Optional<User> findUserByLogin(String login);

    User findByLoginAndPassword(String login, String password);
}
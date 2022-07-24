package by.teachmeskills.eshop.services;

import by.teachmeskills.eshop.dto.CategoryDto;
import by.teachmeskills.eshop.dto.UserDto;
import by.teachmeskills.eshop.entities.User;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface UserService extends BaseService<User> {
    List<CategoryDto> authenticate(UserDto userForLoginDto);

    UserDto registerNewUser(UserDto userForLoginDto);

    UserDto getDataAboutLoggedInUserPaging(int userId, int pageNumber);
}

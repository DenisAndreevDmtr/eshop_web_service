package by.teachmeskills.eshop.services.impl;

import by.teachmeskills.eshop.dto.CategoryDto;
import by.teachmeskills.eshop.dto.UserDto;
import by.teachmeskills.eshop.dto.converters.CategoryConverter;
import by.teachmeskills.eshop.dto.converters.OrderConverter;
import by.teachmeskills.eshop.dto.converters.UserConverter;
import by.teachmeskills.eshop.entities.Category;
import by.teachmeskills.eshop.entities.Order;
import by.teachmeskills.eshop.entities.User;
import by.teachmeskills.eshop.repositories.OrderRepository;
import by.teachmeskills.eshop.repositories.ProductRepository;
import by.teachmeskills.eshop.repositories.UserRepository;
import by.teachmeskills.eshop.services.CategoryService;
import by.teachmeskills.eshop.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CategoryConverter categoryConverter;
    private final UserConverter userConverter;
    private final OrderConverter orderConverter;

    public UserServiceImpl(UserRepository userRepository, CategoryService categoryService, OrderRepository orderRepository, ProductRepository productRepository, CategoryConverter categoryConverter, UserConverter userConverter, OrderConverter orderConverter) {
        this.userRepository = userRepository;
        this.categoryService = categoryService;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.categoryConverter = categoryConverter;
        this.userConverter = userConverter;
        this.orderConverter = orderConverter;
    }

    @Override
    public User create(User entity) {
        return userRepository.create(entity);
    }

    @Override
    public List<User> read() {
        return userRepository.read();
    }

    @Override
    public User update(User entity) {
        return userRepository.update(entity);
    }

    @Override
    public void delete(int id) {
        userRepository.delete(id);
    }

    @Override
    public List<CategoryDto> authenticate(UserDto userDto) {
        if (Optional.ofNullable(userDto).isPresent()
                && Optional.ofNullable(userDto.getLogin()).isPresent()
                && Optional.ofNullable(userDto.getPassword()).isPresent()) {
            User user = userConverter.fromDto(userDto);
            if (userRepository.getUserByLoginAndPassword(user.getLogin(), user.getPassword()).isPresent()) {
                User loggedInUser = userRepository.getUserByLoginAndPassword(user.getLogin(), user.getPassword()).get();
                user.setId(loggedInUser.getId());
                List<Category> categoriesList = categoryService.read();
                return categoriesList.stream().map(categoryConverter::toDto).toList();
            }
        }
        return null;
    }

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user = userConverter.fromDto(userDto);
        if (userRepository.getUserByLogin(user.getLogin()).isEmpty()) {
            User registeredUser = userRepository.create(user);
            return userConverter.toDto(registeredUser);
        } else {
            return null;
        }
    }

    private void populateError(String field, ModelAndView modelAndView, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors(field)) {
            modelAndView.addObject(field + "Error", Objects.requireNonNull(bindingResult.getFieldError(field))
                    .getDefaultMessage());
        }
    }

    @Override
    public UserDto getDataAboutLoggedInUserPaging(User user, int userId, int number) {
        try {
            User loggedInUser = userRepository.getUserById(userId);
            List<Order> ordersList = orderRepository.getAllOrdersByUserId(userId, number);
            UserDto userDto = UserDto.builder()
                    .id(loggedInUser.getId())
                    .login(loggedInUser.getLogin())
                    .password(loggedInUser.getPassword())
                    .name(loggedInUser.getName())
                    .surname(loggedInUser.getSurname())
                    .dateBorn(loggedInUser.getDateBorn())
                    .eMail(loggedInUser.getEMail())
                    .balance(loggedInUser.getBalance())
                    .orders(Optional.ofNullable(ordersList).map(products -> products.stream().map(orderConverter::toDto).toList()).orElse(List.of()))
                    .build();
            return userDto;
        } catch (Exception e) {
            return null;
        }
    }
}
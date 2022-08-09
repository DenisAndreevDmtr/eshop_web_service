package by.teachmeskills.eshop.services.impl;

import by.teachmeskills.eshop.dto.CategoryDto;
import by.teachmeskills.eshop.dto.UserDto;
import by.teachmeskills.eshop.dto.converters.CategoryConverter;
import by.teachmeskills.eshop.dto.converters.OrderConverter;
import by.teachmeskills.eshop.dto.converters.UserConverter;
import by.teachmeskills.eshop.entities.Category;
import by.teachmeskills.eshop.entities.Order;
import by.teachmeskills.eshop.entities.Role;
import by.teachmeskills.eshop.entities.User;
import by.teachmeskills.eshop.repositories.OrderRepository;
import by.teachmeskills.eshop.repositories.ProductRepository;
import by.teachmeskills.eshop.repositories.RoleRepository;
import by.teachmeskills.eshop.repositories.UserRepository;
import by.teachmeskills.eshop.services.CategoryService;
import by.teachmeskills.eshop.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

import java.util.Optional;

import static by.teachmeskills.eshop.utils.EshopConstants.ROLE_USER;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final CategoryService categoryService;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CategoryConverter categoryConverter;
    private final UserConverter userConverter;
    private final OrderConverter orderConverter;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserServiceImpl(CategoryService categoryService, OrderRepository orderRepository, ProductRepository productRepository, CategoryConverter categoryConverter, UserConverter userConverter, OrderConverter orderConverter, RoleRepository roleRepository, @Lazy PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.categoryService = categoryService;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.categoryConverter = categoryConverter;
        this.userConverter = userConverter;
        this.orderConverter = orderConverter;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    @Override
    public User create(User entity) {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        Optional<Role> role = roleRepository.findRoleByName(ROLE_USER);
        if (role.isPresent()) {
            entity.setRole(role.get());
        }
        return userRepository.save(entity);
    }

    @Override
    public List<User> read() {
        return userRepository.findAll();
    }

    @Override
    public User update(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public void delete(int id) {
        userRepository.deleteUserById(id);
    }

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user = userConverter.fromDto(userDto);
        if (userRepository.getUserByLogin(user.getLogin()).isEmpty()) {
            User registeredUser = create(user);
            return userConverter.toDto(registeredUser);
        } else {
            log.info("cant register user");
            return null;
        }
    }

    @Override
    public UserDto getDataAboutLoggedInUser(int userId, int pageNumber, int pageSize) {
        try {
            User loggedInUser = userRepository.findById(userId).get();
            Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by("id").descending());
            Page<Order> ordersList = orderRepository.getOrdersByUserId(userId, paging);
            UserDto userDto = UserDto.builder()
                    .id(loggedInUser.getId())
                    .login(loggedInUser.getLogin())
                    .password(loggedInUser.getPassword())
                    .name(loggedInUser.getName())
                    .surname(loggedInUser.getSurname())
                    .dateBorn(loggedInUser.getDateBorn())
                    .eMail(loggedInUser.getEMail())
                    .balance(loggedInUser.getBalance())
                    .orders(Optional.of(ordersList.getContent()).map(products -> products.stream().map(orderConverter::toDto).toList()).orElse(List.of()))
                    .build();
            return userDto;
        } catch (Exception e) {
            log.info("cant get data");
            return null;
        }
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }

    @Override
    public User findByLoginAndPassword(String login, String password) {
        Optional<User> user = findUserByLogin(login);

        if (user.isPresent()) {
            if (passwordEncoder.matches(password, user.get().getPassword())) {
                return user.get();
            }
        }
        return null;
    }
}
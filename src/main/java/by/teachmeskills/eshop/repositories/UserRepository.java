package by.teachmeskills.eshop.repositories;

import by.teachmeskills.eshop.entities.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {
    User getUserById(int id);

    Optional<User> getUserByLoginAndPassword(String login, String password);

    Optional<User> getUserByLogin(String login);
}
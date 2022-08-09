package by.teachmeskills.eshop.repositories;

import by.teachmeskills.eshop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> getUserByLogin(String login);

    void deleteUserById(int id);

    Optional<User> findUserByLogin(String login);
}
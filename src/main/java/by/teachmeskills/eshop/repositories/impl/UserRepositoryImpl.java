package by.teachmeskills.eshop.repositories.impl;

import by.teachmeskills.eshop.entities.User;
import by.teachmeskills.eshop.repositories.UserRepository;

import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User getUserById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public Optional<User> getUserByLoginAndPassword(String login, String password) {
        Query query = entityManager.createQuery("select u from User u where u.login=:login and u.password=:password");
        query.setParameter("login", login);
        query.setParameter("password", password);
        try {
            return Optional.ofNullable((User) query.getSingleResult());
        } catch (Exception e) {
            System.out.println("Not found such user");
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        Query query = entityManager.createQuery("select u from User u where u.login=:login");
        query.setParameter("login", login);
        try {
            return Optional.ofNullable((User) query.getSingleResult());
        } catch (Exception e) {
            System.out.println("Not unique user");
        }
        return Optional.empty();
    }

    @Override
    public User create(User entity) {
        entityManager.persist(entity);
        return entity;
    }

    //method should be updated
    @Override
    public List<User> read() {
        return entityManager.createQuery("select u from User u").getResultList();
    }

    //method should be updated
    @Override
    public User update(User entity) {
        User user = entityManager.find(User.class, entity.getId());
        user.setName(entity.getName());
        user.setSurname(entity.getSurname());
        user.setBalance(entity.getBalance());
        user.setDateBorn(entity.getDateBorn());
        user.setLogin(entity.getLogin());
        user.setPassword(entity.getPassword());
        entityManager.persist(user);
        return user;
    }

    //method should be updated
    @Override
    public void delete(int id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }
}
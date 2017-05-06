package org.unstoppable.montao.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unstoppable.montao.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
@Transactional
public class UserDAOJpa implements UserDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public void delete(User user) {
        entityManager.remove(user);
    }

    @Override
    public User update(User user) {
        return entityManager.merge(user);
    }

    @Override
    public List<User> findAll(int page, int limit) {
        String jpql = "SELECT u FROM User u";
        return entityManager.createQuery(jpql,User.class)
            .setMaxResults(limit)
            .setFirstResult((page-1)*limit)
            .getResultList();
    }

    @Override
    public User findByUsername(String username) {
        String jpql = "SELECT u FROM User u WHERE u.username = :username";//Выбрать обекты класса User из таблицы User у которых поле username свопадает с передаваемым параметром username
        return entityManager.createQuery(jpql,User.class)
            .setParameter("username", username)//передаваемый параметр username
            .getResultList()
            .stream()
            .findFirst()
            .orElseThrow(NoSuchElementException("User with $username username not found"));
    }

    @Override
    public User findByEmail(String email) {
        String jpql = "SELECT u FROM User u WHERE u.email=:email";
        return entityManager.createQuery(jpql,User.class)
            .setParameter("email", email)
            .getResultList()
            .stream()
            .findFirst()
            .orElseThrow(NoSuchElementException("User with $username username not found"));
    }

    @Override
    public long totalCount() {
        String jpql = "SELECT COUNT(u.id) FROM User u";
        return (long) entityManager
            .createQuery(jpql, User.class)
            .getSingleResult();
    }
}

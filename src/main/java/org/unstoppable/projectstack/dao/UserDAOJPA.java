package org.unstoppable.projectstack.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unstoppable.projectstack.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.List;

/**
 * JPA implementation of UserDAO.
 */
@Repository
@Transactional
public class UserDAOJPA implements UserDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAll() {
        String jpql = "FROM User";
        return entityManager.createQuery(jpql, User.class).getResultList();
    }

    @Override
    public User getById(BigInteger id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void delete(User user) {
        entityManager.remove(user);
    }

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public User getByUsername(String username) {
        String jpql = "FROM User WHERE username = :username";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }

    @Override
    public User getByEmail(String email) {
        String jpql = "FROM User where email = :email";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }
}

package org.unstoppable.montao.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unstoppable.montao.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

@Repository
@Transactional
public class UserDAOJpa implements UserDAO {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<User> getAll() {
        String jql = "from User";
        return entityManager.createQuery(jql, User.class).getResultList();
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
        String jql = "from User where username = :username";
        return entityManager.createQuery(jql, User.class)
            .setParameter("username", username)
            .getResultList().stream()
            .findFirst()
            .orElse(null);
    }

    @Override
    public User getByEmail(String email) {
        String jql = "from User where email = :email";
        return entityManager.createQuery(jql, User.class)
            .setParameter("email", email)
            .getResultList().stream()
            .findFirst()
            .orElse(null);
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public User getByUUID(String token) {
        String jql = "from User where uuid = :uuid";
        return entityManager.createQuery(jql, User.class)
            .setParameter("uuid", token)
            .getResultList().stream()
            .findFirst()
            .orElse(null);
    }

    @Override
    public long getTotalCount() {
        String jql = "select count(id) from User";
        Query query = entityManager.createQuery(jql, User.class);
        return (long) query.getSingleResult();
    }
}

package org.unstoppable.projectstack.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unstoppable.projectstack.entity.User;

import java.math.BigInteger;
import java.util.List;

/**
 * Hibernate implementation of UserDAO.
 */
@Repository
@Transactional
public class UserDAOHibernate implements UserDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<User> getAll() {
        String hql = "FROM User";
        return sessionFactory.getCurrentSession().createQuery(hql, User.class).list();
    }

    @Override
    public User getById(BigInteger id) {
        return sessionFactory.getCurrentSession().find(User.class, id);
    }

    @Override
    public void delete(User user) {
        sessionFactory.getCurrentSession().delete(user);
    }

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public User getByUsername(String username) {
        String hql = "FROM User WHERE username = :username";
        Query<User> query = sessionFactory.getCurrentSession().createQuery(hql, User.class);
        query.setParameter("username", username);
        return query.uniqueResult();
    }

    @Override
    public User getByEmail(String email) {
        String hql = "FROM User WHERE email = :email";
        Query<User> query = sessionFactory.getCurrentSession().createQuery(hql, User.class);
        query.setParameter("email", email);
        return query.uniqueResult();
    }

    @Override
    public void update(User user) {
        sessionFactory.getCurrentSession().update(user);
    }
}

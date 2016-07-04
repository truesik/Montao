package org.unstoppable.projectstack.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unstoppable.projectstack.entity.User;

import java.math.BigInteger;
import java.util.List;

@Repository
public class UserDAOHibernate implements UserDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public List<User> getAll() {
        String hql = "FROM User";
        return sessionFactory.getCurrentSession().createQuery(hql, User.class).list();
    }

    @Override
    @Transactional
    public User getById(BigInteger id) {
        return sessionFactory.getCurrentSession().find(User.class, id);
    }

    @Override
    @Transactional
    public void delete(User user) {
        sessionFactory.getCurrentSession().delete(user);
    }

    @Override
    @Transactional
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @Transactional
    public User getUserByUsername(String username) {
        String hql = "FROM User WHERE username = :username";
        Query<User> query = sessionFactory.getCurrentSession().createQuery(hql, User.class);
        query.setParameter("username", username);
        return query.uniqueResult();
    }

    @Override
    @Transactional
    public User getUserByEmail(String email) {
        String hql = "FROM User WHERE email = :email";
        Query<User> query = sessionFactory.getCurrentSession().createQuery(hql, User.class);
        query.setParameter("email", email);
        return query.uniqueResult();
    }
}

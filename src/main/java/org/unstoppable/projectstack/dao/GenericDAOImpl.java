package org.unstoppable.projectstack.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Generic DAO implementation.
 *
 * @param <T>
 */
@Repository
public class GenericDAOImpl<T> implements GenericDAO<T> {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public List<T> getAll(Class<T> type) {
        String hql = "FROM " + type.getName();
        return sessionFactory.getCurrentSession().createQuery(hql, type).list();
    }

    @Override
    @Transactional
    public T getById(Class<T> type, Long id) {
        return sessionFactory.getCurrentSession().get(type, id);
    }

    @Override
    @Transactional
    public void delete(T object) {
        sessionFactory.getCurrentSession().delete(object);
    }

    @Override
    @Transactional
    public void add(T object) {
        sessionFactory.getCurrentSession().saveOrUpdate(object);
    }

    @Override
    @Transactional
    public T findBy(Class<T> type, String column, String object) {
        String hql = "FROM " + type.getName() + " WHERE " + column + " = :object";
        Query<T> query = sessionFactory.getCurrentSession().createQuery(hql, type);
        query.setParameter("object", object);
        return query.uniqueResult();
    }
}

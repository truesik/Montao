package org.unstoppable.projectstack.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Generic DAO implementation.
 *
 * @param <T>
 */
@Component
public class GenericDAOImpl<T> implements GenericDAO<T> {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<T> getAll(Class<T> type) {
        return null;
    }

    @Override
    public T getById(Class<T> type, Long id) {
        return null;
    }

    @Override
    public Boolean delete(T object) {
        return null;
    }

    @Override
    public T add(T object) {
        return null;
    }
}

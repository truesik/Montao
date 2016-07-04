package org.unstoppable.projectstack.dao;

import java.util.List;

/**
 * Generic CRUD operations.
 *
 * @param <T>
 */
public interface GenericDAO<T> {
    /**
     * Returns object list.
     *
     * @param type Objects class type.
     * @return List.
     */
    List<T> getAll(Class<T> type);

    /**
     * Returns object.
     *
     * @param type Object class type.
     * @param id   Object id.
     * @return Object.
     */
    T getById(Class<T> type, Long id);

    /**
     * Removes the object.
     *
     * @param object Object.
     */
    void delete(T object);

    /**
     * Adds object to db.
     *
     * @param object Object.
     */
    void add(T object);

    /**
     * Find by...
     *
     * @param type   Object class type,
     * @param column Column.
     * @param object By what.
     * @return Object.
     */
    T findBy(Class<T> type, String column, String object);
}

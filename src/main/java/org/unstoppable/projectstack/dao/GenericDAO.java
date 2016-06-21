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
     * @return True or false.
     */
    Boolean delete(T object);

    /**
     * Adds object to db.
     *
     * @param object Object.
     * @return Object.
     */
    T add(T object);
}

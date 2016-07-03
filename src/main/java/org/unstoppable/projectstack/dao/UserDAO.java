package org.unstoppable.projectstack.dao;

import org.unstoppable.projectstack.entity.User;

import java.math.BigInteger;
import java.util.List;

public interface UserDAO {
    /**
     * Returns object list.
     *
     * @param type Objects class type.
     * @return List.
     */
    List<User> getAll();

    /**
     * Returns object.
     *
     * @param type Object class type.
     * @param id   Object id.
     * @return Object.
     */
    User getById(BigInteger id);

    /**
     * Removes the object.
     *
     * @param object Object.
     */
    void delete(User user);

    /**
     * Adds object to db.
     *
     * @param object Object.
     */
    void add(User user);

    /**
     *
     * @param type
     * @param s
     * @return
     */
    User getUserByUsername(String username);
}

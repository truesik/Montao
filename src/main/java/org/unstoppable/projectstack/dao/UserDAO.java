package org.unstoppable.projectstack.dao;

import org.unstoppable.projectstack.entity.User;

import java.util.List;

/**
 * CRUD operations with table USERS.
 */
public interface UserDAO {
    /**
     * Gets all users from db.
     *
     * @return List.
     */
    List<User> getAll();

    /**
     * Gets user by unique id.
     *
     * @param id User id.
     * @return User.
     */
    User getUserById(Long id);

    /**
     * Adds user to db.
     *
     * @param user User.
     */
    void add(User user);

    /**
     * Removes the user.
     *
     * @param user User,
     */
    void delete(User user);
}

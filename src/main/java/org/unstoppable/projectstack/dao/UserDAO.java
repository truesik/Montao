package org.unstoppable.projectstack.dao;

import org.unstoppable.projectstack.entity.User;

import java.math.BigInteger;
import java.util.List;

public interface UserDAO {
    /**
     * Returns user list.
     *
     * @return User list.
     */
    List<User> getAll();

    /**
     * Returns users by id.
     *
     * @param id User id.
     * @return User.
     */
    User getById(BigInteger id);

    /**
     * Removes user.
     *
     * @param user User.
     */
    void delete(User user);

    /**
     * Adds user to db.
     *
     * @param user User.
     */
    void add(User user);

    /**
     * Returna user by username.
     *
     * @param username Username.
     * @return User,
     */
    User getByUsername(String username);

    /**
     * Returns user by email.
     *
     * @param email Email.
     * @return User.
     */
    User getByEmail(String email);
}

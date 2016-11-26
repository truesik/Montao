package org.unstoppable.montao.dao;

import org.unstoppable.montao.entity.User;

import java.math.BigInteger;
import java.util.List;

/**
 * Layer that works with User table in DB.
 */
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

    /**
     * Update user fields.
     *
     * @param user User.
     */
    void update(User user);

    /**
     * Returns user by uuid.
     *
     * @param token uuid.
     * @return User.
     */
    User getByUUID(String token);
}

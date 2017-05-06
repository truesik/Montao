package org.unstoppable.montao.dao;

import org.unstoppable.montao.entity.User;

import java.math.BigInteger;
import java.util.List;

/**
 * Layer that works with User table in DB.
 */
public interface UserDAO {
    public void add(User user);

    public void delete(User user);

    public User update(User user);

    public List<User> findAll (int page, int limit);

    public User findByUsername(String username);

    public User findByEmail(String email);

    public Long totalCount();
}

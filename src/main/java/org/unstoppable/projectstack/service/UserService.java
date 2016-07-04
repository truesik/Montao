package org.unstoppable.projectstack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unstoppable.projectstack.dao.UserDAO;
import org.unstoppable.projectstack.entity.User;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    /**
     * Used to add user to db.
     *
     * @param user User.
     */
    public void add(User user) {
        user.setRole("ROLE_USER");
        user.setRegistrationDate(LocalDate.now());
        userDAO.add(user);
    }

    /**
     * Returns all users from db.
     *
     * @return List of users.
     */
    public List<User> getAll() {
        return userDAO.getAll();
    }

    /**
     * Returns user from db.
     *
     * @param id User id.
     * @return User.
     */
    public User getById(BigInteger id) {
        return userDAO.getById(id);
    }

    /**
     * Removes user from db.
     *
     * @param user User.
     */
    public void delete(User user) {
        userDAO.delete(user);
    }

    /**
     * Find user by username.
     *
     * @param username Username.
     * @return User.
     */
    public User getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    /**
     * Returns false if user found and true if not.
     *
     * @param username Username.
     * @return True or false.
     */
    public Boolean checkUsername(String username) {
        User user = userDAO.getUserByUsername(username);
        return user == null;
    }

    /**
     * Returns false if user found and true if not.
     *
     * @param email email.
     * @return True or false.
     */
    public Boolean checkEmail(String email) {
        User user = userDAO.getUserByEmail(email);
        return user == null;
    }
}

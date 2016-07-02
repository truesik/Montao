package org.unstoppable.projectstack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unstoppable.projectstack.dao.GenericDAO;
import org.unstoppable.projectstack.entity.User;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private GenericDAO<User> genericDAO;

    /**
     * Used to add user to db.
     *
     * @param user User.
     */
    public void add(User user) {
        user.setRole("ROLE_USER");
        user.setRegistrationDate(LocalDate.now());
        genericDAO.add(user);
    }

    /**
     * Returns all users from db.
     *
     * @return List of users.
     */
    public List<User> getAll() {
        return genericDAO.getAll(User.class);
    }

    /**
     * Returns user from db.
     *
     * @param id User id.
     * @return
     */
    public User getById(Long id) {
        return genericDAO.getById(User.class, id);
    }

    /**
     * Removes user from db.
     *
     * @param user User.
     */
    public void delete(User user) {
        genericDAO.delete(user);
    }
}

package org.unstoppable.projectstack.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.unstoppable.projectstack.entity.User;

import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Used to validate new user information before registration.
 */
public class UserRegistrationForm {
    private static final int MIN_USERNAME_LENGTH = 4;
    private static final int MIN_PASSWORD_LENGTH = 8;

    @NotEmpty
    @Size(min = MIN_USERNAME_LENGTH)
    private String username;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    @Size(min = MIN_PASSWORD_LENGTH)
    private String password;

    @NotEmpty
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * Creates user based on information from registration form.
     *
     * @return User.
     */
    public User createUser() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setRole("ROLE_USER");
        user.setConfirmed(false);
        user.setLocked(false);
        user.setRegistrationDate(LocalDate.now());
        return user;
    }
}

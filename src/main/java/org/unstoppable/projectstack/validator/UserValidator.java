package org.unstoppable.projectstack.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.unstoppable.projectstack.entity.User;
import org.unstoppable.projectstack.service.UserService;

public class UserValidator implements Validator {
    private UserService userService;

    public UserValidator(UserService userService) {
        if (userService == null) {
            throw new IllegalArgumentException("The supplied [Service] is required and must not be null.");
        }
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        usernameValidation(errors, user);
        emailValidation(errors, user);
    }

    /**
     * This method is used to validate email field.
     *
     * @param errors Errors.
     * @param user   User.
     */
    private void emailValidation(Errors errors, User user) {
        // Check email existence
        if (!userService.checkEmail(user.getEmail())) {
            errors.rejectValue("email", "Email already registered.");
        }
    }

    /**
     * This method is used to validate username field.
     *
     * @param errors Errors.
     * @param user   User.
     */
    private void usernameValidation(Errors errors, User user) {
        // Only latin chars, numbers and "_"
        if (!user.getUsername().matches("\\w+")) {
            errors.rejectValue("username", "Wrong characters in username field.");
        }
        // Check username existence
        if (!userService.checkUsername(user.getUsername())) {
            errors.rejectValue("username", "Username already registered.");
        }
    }
}

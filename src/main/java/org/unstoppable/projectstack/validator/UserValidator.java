package org.unstoppable.projectstack.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.unstoppable.projectstack.entity.User;
import org.unstoppable.projectstack.service.UserService;

public class UserValidator implements Validator {
    private UserService userService;

    public UserValidator(UserService userService) {
        if (userService == null) {
            throw new IllegalArgumentException("The supplied [Service] is " +
                    "required and must not be null.");
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

    private void emailValidation(Errors errors, User user) {
        if (!userService.checkEmail(user.getEmail())) {
            errors.rejectValue("email", "Email already registered.");
        }
    }

    private void usernameValidation(Errors errors, User user) {
        if (!userService.checkUsername(user.getUsername())) {
            errors.rejectValue("username", "Username already registered.");
        }
    }
}

package org.unstoppable.projectstack.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.unstoppable.projectstack.entity.User;
import org.unstoppable.projectstack.model.UserRegistrationForm;
import org.unstoppable.projectstack.service.CommunityService;
import org.unstoppable.projectstack.service.UserService;

public class UserValidator implements Validator {
    private UserService userService;
    private CommunityService communityService;

    public UserValidator(UserService userService, CommunityService communityService) {
        if (userService == null || communityService == null) {
            throw new IllegalArgumentException("The supplied [Service] is required and must not be null.");
        }
        this.userService = userService;
        this.communityService = communityService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserRegistrationForm registrationForm = (UserRegistrationForm) o;
        usernameValidation(errors, registrationForm);
        emailValidation(errors, registrationForm);
        passwordValidation(errors, registrationForm);
    }

    /**
     * This method is used to validate confirm password field.
     *
     * @param errors           Errors.
     * @param registrationForm New user.
     */
    private void passwordValidation(Errors errors, UserRegistrationForm registrationForm) {
        if (!registrationForm.getPassword().equals(registrationForm.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "user.error.password.confirm");
        }
    }

    /**
     * This method is used to validate email field.
     *
     * @param errors           Errors.
     * @param registrationForm New user.
     */
    private void emailValidation(Errors errors, UserRegistrationForm registrationForm) {
        // Check email existence
        if (!userService.checkEmail(registrationForm.getEmail())) {
            errors.rejectValue("email", "user.error.email.already_registered");
        }
    }

    /**
     * This method is used to validate username field.
     *
     * @param errors           Errors.
     * @param registrationForm New user.
     */
    private void usernameValidation(Errors errors, UserRegistrationForm registrationForm) {
        // Only latin chars, numbers and "_"
        if (!registrationForm.getUsername().matches("\\w+")) {
            errors.rejectValue("username", "user.error.username.chars");
        }
        // Check username existence
        if (!userService.checkUsername(registrationForm.getUsername())) {
            errors.rejectValue("username", "user.error.username.already_registered");
        }
        // Check community title existence with same name
        if (!communityService.checkTitle(registrationForm.getUsername())) {
            errors.rejectValue("username", "user.error.username.already_registered");
        }
    }
}

package org.unstoppable.projectstack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.unstoppable.projectstack.entity.User;
import org.unstoppable.projectstack.model.UserRegistrationForm;
import org.unstoppable.projectstack.service.UserService;
import org.unstoppable.projectstack.validator.UserValidator;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/user")
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addUser(@Valid @RequestBody UserRegistrationForm userForm, BindingResult result) {
        new UserValidator(userService).validate(userForm, result);
        if (result.hasErrors()) {
            return "failure";
        } else {
            User user = userForm.createUser();
            userService.registerNewUser(user);
            return "success";
        }
    }

    @RequestMapping(value = "/check_username", method = RequestMethod.POST)
    public String checkUsername(String username) {
        return userService.checkUsername(username).toString();
    }

    @RequestMapping(value = "/check_email", method = RequestMethod.POST)
    public String checkEmail(String email) {
        return userService.checkEmail(email).toString();
    }
}

package org.unstoppable.projectstack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.unstoppable.projectstack.entity.User;
import org.unstoppable.projectstack.model.UserRegistrationForm;
import org.unstoppable.projectstack.service.UserService;
import org.unstoppable.projectstack.validator.UserValidator;

import javax.validation.Valid;

/**
 * Registration.html controller.
 */
@Controller
@RequestMapping(value = "/registration")
public class RegistrationController {
    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get registration.html.
     *
     * @param model Page.
     * @return registration.html
     */
    @RequestMapping(method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new UserRegistrationForm());
        return "registration";
    }

    /**
     * Used to add new user to db.
     *
     * @param userForm New user registration form.
     * @param result   Errors.
     * @return Page.
     */
    @RequestMapping(method = RequestMethod.POST)
    public String addUser(@Valid @ModelAttribute("userForm") UserRegistrationForm userForm, BindingResult result) {
        new UserValidator(userService).validate(userForm, result);
        if (result.hasErrors()) {
            return "registration";
        } else {
            User user = userForm.createUser();
            userService.registerNewUser(user);
//            userService.sendConfirmRegistrationMessage(user);
            return "redirect:/success";
        }
    }

    /**
     * Returns false if username already exist and true if not.
     *
     * @param username Username.
     * @return String "true" or "false".
     */
    @RequestMapping(value = "/check_username", method = RequestMethod.POST)
    @ResponseBody
    public String checkUsername(String username) {
        return userService.checkUsername(username).toString();
    }

    /**
     * Returns false if email already exist and true if not.
     *
     * @param email Email.
     * @return String "true" or "false".
     */
    @RequestMapping(value = "/check_email", method = RequestMethod.POST)
    @ResponseBody
    public String checkEmail(String email) {
        return userService.checkEmail(email).toString();
    }

    /**
     * Used to account confirmation after registration.
     *
     * @param token Unique user id.
     * @param model Page renderer.
     * @return Page.
     */
    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public String confirmRegistration(@RequestParam String token, Model model) {
        User user = userService.getByToken(token);
        if (user != null) {
            if (!user.isConfirmed()) {
                user.setConfirmed(true);
                userService.update(user);
                model.addAttribute("message", "Your account has been successfully verified");
                return "message";
            } else {
                model.addAttribute("message", "User already confirmed");
                return "message";
            }
        } else {
            model.addAttribute("message", "No such token exist");
            return "message";
        }
    }
}

package org.unstoppable.projectstack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.unstoppable.projectstack.entity.User;
import org.unstoppable.projectstack.service.UserService;

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
        model.addAttribute("user", new User());
        return "registration";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addUser(@Valid @ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "registration";
        } else {
            userService.add(user);
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
}

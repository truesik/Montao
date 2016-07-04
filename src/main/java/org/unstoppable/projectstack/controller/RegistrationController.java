package org.unstoppable.projectstack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.unstoppable.projectstack.entity.User;
import org.unstoppable.projectstack.service.UserService;

/**
 * Registration.html controller.
 */
@Controller
@RequestMapping(value = "/registration")
public class RegistrationController {
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/success";
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

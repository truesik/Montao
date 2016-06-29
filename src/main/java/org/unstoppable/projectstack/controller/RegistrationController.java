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
        user.setRole("ROLE_USER");
        userService.add(user);
        return "redirect:/";
    }
}

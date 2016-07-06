package org.unstoppable.projectstack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.unstoppable.projectstack.entity.User;
import org.unstoppable.projectstack.service.UserService;

import java.math.BigInteger;

/**
 * Profile.html controller.
 */
@Controller
public class ProfileController {
    @Autowired
    private UserService userService;

    /**
     * Get user profile by username.
     *
     * @param username Username.
     * @param model    Page.
     * @return Rendered page.
     */
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public String userProfileByUsername(@PathVariable("username") String username, Model model) {
        User user = userService.getByUsername(username);
        if (user == null) {
            return "redirect:/";
        } else {
            model.addAttribute("user", user);
            return "profile";
        }
    }

    /**
     * Get user profile by id.
     *
     * @param id    User id.
     * @param model Page.
     * @return Rendered page.
     */
    @RequestMapping(value = "/id{id}", method = RequestMethod.GET)
    public String userProfileById(@PathVariable("id") BigInteger id, Model model) {
        User user = userService.getById(id);
        if (user == null) {
            return "redirect:/";
        } else {
            model.addAttribute("user", user);
            return "profile";
        }
    }
}

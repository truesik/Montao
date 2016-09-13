package org.unstoppable.projectstack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
    /**
     * Login page.
     *
     * @return signin.html
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "signin";
    }

    /**
     * Login page with error.
     *
     * @param model Page renderer.
     * @return signin.html
     */
    @RequestMapping(value = "/login=error", method = RequestMethod.GET)
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "signin";
    }
}

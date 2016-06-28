package org.unstoppable.projectstack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Registration.html controller.
 */
@Controller
@RequestMapping(value = "/registration")
public class RegistrationController {
    @RequestMapping(method = RequestMethod.GET)
    public String registration() {
        return "registration";
    }
}

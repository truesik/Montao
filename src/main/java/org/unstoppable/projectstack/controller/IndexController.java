package org.unstoppable.projectstack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Index.html controller.
 */
@Controller
@RequestMapping(value = "/")
public class IndexController {
    /**
     * This method is used to open main page.
     *
     * @return index.html
     */
    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "index";
    }
}

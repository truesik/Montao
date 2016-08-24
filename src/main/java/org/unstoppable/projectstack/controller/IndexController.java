package org.unstoppable.projectstack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.unstoppable.projectstack.entity.Community;
import org.unstoppable.projectstack.service.CommunityService;

import java.util.List;

/**
 * Index.html controller.
 */
@Controller
@RequestMapping(value = "/")
public class IndexController {
    private final CommunityService communityService;

    @Autowired
    public IndexController(CommunityService communityService) {
        this.communityService = communityService;
    }

    /**
     * This method is used to open main page.
     *
     * @param model Page renderer.
     * @return index.html
     */
    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        List<Community> communities = communityService.getPublicCommunities(0, 40);
        model.addAttribute("communities", communities);
        return "index";
    }
}

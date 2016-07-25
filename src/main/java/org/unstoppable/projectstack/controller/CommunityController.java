package org.unstoppable.projectstack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.unstoppable.projectstack.entity.Community;
import org.unstoppable.projectstack.entity.User;
import org.unstoppable.projectstack.model.CommunityCreationForm;
import org.unstoppable.projectstack.service.CommunityService;
import org.unstoppable.projectstack.service.UserService;
import org.unstoppable.projectstack.validator.CommunityValidator;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/communities")
public class CommunityController {
    private final UserService userService;
    private final CommunityService communityService;

    @Autowired
    public CommunityController(UserService userService, CommunityService communityService) {
        this.userService = userService;
        this.communityService = communityService;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String createNewCommunityPage(Model model, Principal principal) {
        model.addAttribute("communityForm", new CommunityCreationForm());
        User user = userService.getByUsername(principal.getName());
        model.addAttribute("user", user);
        return "newcommunity";
    }

    /**
     * Used to add new community to db.
     *
     * @param communityForm Community creation form.
     * @param result        Errors.
     * @param model         Model.
     * @param principal     Principal.
     * @return Page.
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String createNewCommunity(@Valid @ModelAttribute("communityForm") CommunityCreationForm communityForm, BindingResult result, Model model, Principal principal) {
        new CommunityValidator(communityService).validate(communityForm, result);
        if (result.hasErrors()) {
            User user = userService.getByUsername(principal.getName());
            model.addAttribute("user", user);
            return "newcommunity";
        } else {
            Community community = communityForm.createCommunity();
            communityService.save(community);
            return "redirect:/" + community.getTitle();
        }
    }

    /**
     * Returns false if title already exist and true if not.
     *
     * @param title Community title.
     * @return String "true" or "false".
     */
    @RequestMapping(value = "/check_title", method = RequestMethod.POST)
    @ResponseBody
    public String checkTitle(String title) {
        return communityService.checkTitle(title).toString();
    }
}

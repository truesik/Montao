package org.unstoppable.projectstack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.unstoppable.projectstack.entity.Channel;
import org.unstoppable.projectstack.entity.Community;
import org.unstoppable.projectstack.entity.User;
import org.unstoppable.projectstack.model.CommunityCreationForm;
import org.unstoppable.projectstack.service.ChannelService;
import org.unstoppable.projectstack.service.CommunityService;
import org.unstoppable.projectstack.service.UserService;
import org.unstoppable.projectstack.validator.CommunityValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/communities")
public class CommunityController {
    private final UserService userService;
    private final CommunityService communityService;
    private final ChannelService channelService;

    @Autowired
    public CommunityController(UserService userService,
                               CommunityService communityService,
                               ChannelService channelService) {
        this.userService = userService;
        this.communityService = communityService;
        this.channelService = channelService;
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
    public String createNewCommunity(@Valid @ModelAttribute("communityForm") CommunityCreationForm communityForm,
                                     BindingResult result,
                                     Model model,
                                     Principal principal) {
        new CommunityValidator(communityService).validate(communityForm, result);
        if (result.hasErrors()) {
            User user = userService.getByUsername(principal.getName());
            model.addAttribute("user", user);
            return "newcommunity";
        } else {
            Community community = communityForm.createCommunity();
            communityService.save(community);
            // After community creation we should add default channel
            Channel channel = createDefaultChannel(community);
            channelService.add(channel);
            return "redirect:/" + community.getTitle();
        }
    }

    private Channel createDefaultChannel(Community community) {
        Channel channel = new Channel();
        channel.setTitle("General");
        channel.setDescription("Default channel");
        channel.setCommunity(community);
        return channel;
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

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Community> getCommunities(int startRowPosition) {
        return communityService.getPublicCommunities(startRowPosition, 40);
    }
}

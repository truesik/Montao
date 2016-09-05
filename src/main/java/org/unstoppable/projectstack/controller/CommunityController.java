package org.unstoppable.projectstack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.unstoppable.projectstack.entity.Channel;
import org.unstoppable.projectstack.entity.Community;
import org.unstoppable.projectstack.entity.Subscription;
import org.unstoppable.projectstack.entity.User;
import org.unstoppable.projectstack.model.CommunityCreationForm;
import org.unstoppable.projectstack.model.CommunityUser;
import org.unstoppable.projectstack.service.ChannelService;
import org.unstoppable.projectstack.service.CommunityService;
import org.unstoppable.projectstack.service.SubscriptionService;
import org.unstoppable.projectstack.service.UserService;
import org.unstoppable.projectstack.validator.CommunityValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/communities")
public class CommunityController {
    private final UserService userService;
    private final CommunityService communityService;
    private final ChannelService channelService;
    private final SubscriptionService subscriptionService;

    @Autowired
    public CommunityController(UserService userService,
                               CommunityService communityService,
                               ChannelService channelService, SubscriptionService subscriptionService) {
        this.userService = userService;
        this.communityService = communityService;
        this.channelService = channelService;
        this.subscriptionService = subscriptionService;
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
    public List<CommunityUser> getCommunities(int startRowPosition, Principal principal) {
        if (principal != null) {
            User user = userService.getByUsername(principal.getName());
            return subscriptionService.getCommunitySubscriptions(user, startRowPosition, 40);
        }
        List<Community> communities = communityService.getPublicCommunities(startRowPosition, 40);
        List<CommunityUser> communityUsers = new ArrayList<>();
        for (Community community : communities) {
            CommunityUser communityUser = new CommunityUser();
            communityUser.setTitle(community.getTitle());
            communityUser.setDescription(community.getDescription());
            communityUser.setSubscribed(false);
            communityUsers.add(communityUser);
        }
        return communityUsers;
    }

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    @ResponseBody
    public String subscribe(String communityTitle, Principal principal) {
        if (principal != null) {
            Community community = communityService.getByTitle(communityTitle);
            User user = userService.getByUsername(principal.getName());
            subscriptionService.subscribe(createSubscription(community, user));
            return "success";
        }
        return "failure";
    }

    private Subscription createSubscription(Community community, User user) {
        Subscription subscription = new Subscription();
        subscription.setCommunity(community);
        subscription.setUser(user);
        return subscription;
    }

    @RequestMapping(value = "/unsubscribe", method = RequestMethod.POST)
    @ResponseBody
    public String unsubscribe(String communityTitle, Principal principal) {
        if (principal != null) {
            Community community = communityService.getByTitle(communityTitle);
            User user = userService.getByUsername(principal.getName());
            Subscription subscription = subscriptionService.get(community, user);
            subscriptionService.delete(subscription);
            return "success";
        }
        return "failure";
    }
}

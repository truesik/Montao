package org.unstoppable.montao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.unstoppable.montao.entity.Community;
import org.unstoppable.montao.entity.Subscription;
import org.unstoppable.montao.entity.User;
import org.unstoppable.montao.service.CommunityService;
import org.unstoppable.montao.service.SubscriptionService;
import org.unstoppable.montao.service.UserService;

import java.math.BigInteger;
import java.security.Principal;
import java.util.List;

@Controller
public class ProfileController {
    private final UserService userService;
    private final CommunityService communityService;
    private final SubscriptionService subscriptionService;

    @Autowired
    public ProfileController(UserService userService,
                             CommunityService communityService,
                             SubscriptionService subscriptionService) {
        this.userService = userService;
        this.communityService = communityService;
        this.subscriptionService = subscriptionService;
    }

    /**
     * Get user profile by id.
     *
     * @param id    User id.
     * @param model Page.
     * @return Rendered page.
     */
    @RequestMapping(value = "/id{id}", method = RequestMethod.GET)
    public String getUserProfileById(@PathVariable("id") BigInteger id, Model model) {
        User user = userService.getById(id);
        if (user == null) {
            return "redirect:/";
        } else {
            return openUserProfilePage(model, user);
        }
    }

    /**
     * Get user or community page by username or title.
     *
     * @param usernameOrTitle Username.
     * @param model           Page.
     * @return Rendered page.
     */
    @RequestMapping(value = "/{usernameOrCommunityTitle}", method = RequestMethod.GET)
    public String getUserProfileByUsername(@PathVariable("usernameOrCommunityTitle") String usernameOrTitle,
                                           Model model,
                                           Principal principal) {
        User user = userService.getByUsername(usernameOrTitle);
        Community community = communityService.getByTitle(usernameOrTitle);
        if (user != null) {
            return openUserProfilePage(model, user);
        } else if (community != null) {
            return openCommunityPage(model, community, principal);
        } else {
            return "redirect:/";
        }
    }

    /**
     * Returns community page.
     *
     * @param model     Page renderer.
     * @param community Community which page should be opened.
     * @return Page.
     */
    private String openCommunityPage(Model model, Community community, Principal principal) {
        model.addAttribute("channelList", community.getChannels());
        // Fill subscribed user list
        List<Subscription> subscriptions = subscriptionService.getByCommunity(community);
        model.addAttribute("userList", subscriptions);
        if (principal != null) {
            User user = userService.getByUsername(principal.getName());
            Boolean isSubscribed = subscriptionService.checkSubscription(community, user);
            model.addAttribute("subscribed", isSubscribed);
        } else {
            model.addAttribute("subscribed", false);
        }
        return "community";
    }

    /**
     * Returns user profile page.
     *
     * @param model Page renderer.
     * @param user  User which page should be opened.
     * @return Page.
     */
    private String openUserProfilePage(Model model, User user) {
        model.addAttribute("user", user);
        return "profile";
    }
}

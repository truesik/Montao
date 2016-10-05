package org.unstoppable.projectstack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.unstoppable.projectstack.entity.Channel;
import org.unstoppable.projectstack.entity.Community;
import org.unstoppable.projectstack.entity.Subscription;
import org.unstoppable.projectstack.entity.User;
import org.unstoppable.projectstack.model.CommunityCreationForm;
import org.unstoppable.projectstack.model.CommunitySubscription;
import org.unstoppable.projectstack.service.ChannelService;
import org.unstoppable.projectstack.service.CommunityService;
import org.unstoppable.projectstack.service.SubscriptionService;
import org.unstoppable.projectstack.service.UserService;
import org.unstoppable.projectstack.validator.CommunityValidator;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/community")
public class CommunityRestController {
    private static final int QUANTITY = 40;

    private final CommunityService communityService;
    private final UserService userService;
    private final ChannelService channelService;
    private final SubscriptionService subscriptionService;

    @Autowired
    public CommunityRestController(CommunityService communityService,
                                   UserService userService,
                                   ChannelService channelService,
                                   SubscriptionService subscriptionService) {
        this.communityService = communityService;
        this.userService = userService;
        this.channelService = channelService;
        this.subscriptionService = subscriptionService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Void> addCommunity(@Valid @RequestBody CommunityCreationForm communityForm,
                                             BindingResult result,
                                             Principal principal,
                                             UriComponentsBuilder uriComponentsBuilder) {
        new CommunityValidator(communityService).validate(communityForm, result);
        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            Community community = communityForm.createCommunity();
            communityService.save(community);
            // After community creation we should add default channel
            Channel defaultChannel = createDefaultChannel(community);
            channelService.add(defaultChannel);
            // Subscribe creator to that community
            User user = userService.getByUsername(principal.getName());
            subscriptionService.subscribe(createSubscription(community, user));
            // Create header
            HttpHeaders headers = new HttpHeaders();
            // And header location
            URI location = uriComponentsBuilder.path("/{communityTitle}").buildAndExpand(community.getTitle()).toUri();
            headers.setLocation(location);
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
    }

    /**
     * Returns false if title already exist and true if not.
     *
     * @param communityTitle Community title.
     * @return String "true" or "false".
     */
    @RequestMapping(value = "/check_title", method = RequestMethod.POST)
    public String checkTitle(@RequestParam(value = "communityTitle") String communityTitle) {
        return communityService.checkTitle(communityTitle).toString();
    }

    @RequestMapping(value = "/get_all", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CommunitySubscription> getCommunities(int startRowPosition, Principal principal) {
        if (principal != null) {
            User user = userService.getByUsername(principal.getName());
            return subscriptionService.getCommunitiesWithSubscriptionsByUser(user, startRowPosition, QUANTITY);
        } else {
            List<Community> communities = communityService.getPublicCommunities(startRowPosition, QUANTITY);
            List<CommunitySubscription> communitySubscriptions = new ArrayList<>();
            for (Community community : communities) {
                CommunitySubscription communitySubscription = createCommunitySubscription(community);
                communitySubscriptions.add(communitySubscription);
            }
            return communitySubscriptions;
        }
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public ResponseEntity<Void> subscribe(String communityTitle, Principal principal) {
        if (principal != null) {
            Community community = communityService.getByTitle(communityTitle);
            User user = userService.getByUsername(principal.getName());
            subscriptionService.subscribe(createSubscription(community, user));
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @RequestMapping(value = "/leave", method = RequestMethod.POST)
    public ResponseEntity<Void> unsubscribe(String communityTitle, Principal principal) {
        if (principal != null) {
            Community community = communityService.getByTitle(communityTitle);
            User user = userService.getByUsername(principal.getName());
            Subscription subscription = subscriptionService.get(community, user);
            if (subscription != null) {
                subscriptionService.delete(subscription);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    private CommunitySubscription createCommunitySubscription(Community community) {
        CommunitySubscription communitySubscription = new CommunitySubscription();
        communitySubscription.setTitle(community.getTitle());
        communitySubscription.setDescription(community.getDescription());
        communitySubscription.setSubscribed(false);
        return communitySubscription;
    }

    private Channel createDefaultChannel(Community community) {
        Channel channel = new Channel();
        channel.setTitle("general");
        channel.setDescription("Automatically generated channel");
        channel.setCommunity(community);
        return channel;
    }

    private Subscription createSubscription(Community community, User user) {
        Subscription subscription = new Subscription();
        subscription.setCommunity(community);
        subscription.setUser(user);
        return subscription;
    }
}

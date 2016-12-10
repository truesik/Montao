package org.unstoppable.montao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.unstoppable.montao.entity.Channel;
import org.unstoppable.montao.entity.Community;
import org.unstoppable.montao.entity.Subscription;
import org.unstoppable.montao.entity.User;
import org.unstoppable.montao.model.CommunityCreationForm;
import org.unstoppable.montao.model.CommunitySubscription;
import org.unstoppable.montao.service.ChannelService;
import org.unstoppable.montao.service.CommunityService;
import org.unstoppable.montao.service.SubscriptionService;
import org.unstoppable.montao.service.UserService;
import org.unstoppable.montao.validator.CommunityValidator;

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

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addCommunity(@Valid @RequestBody CommunityCreationForm communityForm,
                                       BindingResult result,
                                       UriComponentsBuilder uriComponentsBuilder) {
        new CommunityValidator(communityService).validate(communityForm, result);
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Form validation failed");
        } else {
            // Get User instance by founder username
            User founder = userService.getByUsername(communityForm.getFounder());
            Community community = communityForm.createCommunity(founder);
            communityService.save(community);
            // After community creation we should add default channel
            Channel defaultChannel = createDefaultChannel(community);
            channelService.add(defaultChannel);
            // Subscribe creator to that community
            subscriptionService.subscribe(createSubscription(community, founder));
            // And create location
            URI location = uriComponentsBuilder
                    .path("/community/{communityTitle}")
                    .buildAndExpand(community.getTitle())
                    .toUri();
            return ResponseEntity.created(location).build();
        }
    }

    /**
     * Returns false if title already exist and true if not.
     *
     * @param communityTitle Community title.
     * @return String "true" or "false".
     */
    @PostMapping(value = "/check_title")
    public String checkTitle(String communityTitle) {
        return communityService.checkTitle(communityTitle).toString();
    }

    @PostMapping(value = "/get_all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CommunitySubscription> getCommunities(int startRowPosition, Principal principal) {
        if (principal != null) {
            User user = userService.getByUsername(principal.getName());
            return subscriptionService.getCommunitiesWithSubscriptionsByUser(user, startRowPosition, QUANTITY);
        } else {
            List<Community> communities = communityService.getPublicCommunities(startRowPosition, QUANTITY);
            List<CommunitySubscription> communitySubscriptions = new ArrayList<>();
            for (Community community : communities) {
                CommunitySubscription communitySubscription = createCommunitySubscription(community, false);
                communitySubscriptions.add(communitySubscription);
            }
            return communitySubscriptions;
        }
    }

    @PostMapping(value = "/join")
    public ResponseEntity subscribe(String communityTitle, Principal principal) {
        if (principal != null) {
            Community community = communityService.getByTitle(communityTitle);
            User user = userService.getByUsername(principal.getName());
            subscriptionService.subscribe(createSubscription(community, user));
            return ResponseEntity.ok(createCommunitySubscription(community, true));
        }
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("Allows to authorized users only");
    }

    @PostMapping(value = "/leave")
    public ResponseEntity unsubscribe(String communityTitle, Principal principal) {
        if (principal != null) {
            Community community = communityService.getByTitle(communityTitle);
            User user = userService.getByUsername(principal.getName());
            Subscription subscription = subscriptionService.get(community, user);
            if (subscription != null) {
                subscriptionService.delete(subscription);
                return ResponseEntity.ok(createCommunitySubscription(community, false));
            } else {
                return ResponseEntity.badRequest().body("Subscription not found");
            }
        }
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("Allows to authorized users only");
    }

    @PostMapping(value = "/check_subscription")
    public ResponseEntity<String> checkSubscription(String communityTitle, Principal principal) {
        if (principal == null) {
            return ResponseEntity.ok("false");
        } else {
            User user = userService.getByUsername(principal.getName());
            Community community = communityService.getByTitle(communityTitle);
            Boolean isSubscribed = subscriptionService.checkSubscription(community, user);
            if (isSubscribed) {
                return ResponseEntity.ok(isSubscribed.toString());
            } else {
                return ResponseEntity.ok(isSubscribed.toString());
            }
        }
    }

    private CommunitySubscription createCommunitySubscription(Community community, boolean isSubscribed) {
        CommunitySubscription communitySubscription = new CommunitySubscription();
        communitySubscription.setId(community.getId());
        communitySubscription.setTitle(community.getTitle());
        communitySubscription.setDescription(community.getDescription());
        communitySubscription.setSubscribed(isSubscribed);
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

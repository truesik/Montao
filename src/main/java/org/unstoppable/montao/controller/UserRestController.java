package org.unstoppable.montao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.unstoppable.montao.entity.Community;
import org.unstoppable.montao.entity.Subscription;
import org.unstoppable.montao.entity.User;
import org.unstoppable.montao.model.UserRegistrationForm;
import org.unstoppable.montao.service.CommunityService;
import org.unstoppable.montao.service.SubscriptionService;
import org.unstoppable.montao.service.UserService;
import org.unstoppable.montao.validator.UserValidator;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
public class UserRestController {
    private final UserService userService;
    private final SubscriptionService subscriptionService;
    private final CommunityService communityService;

    @Autowired
    public UserRestController(UserService userService,
                              SubscriptionService subscriptionService,
                              CommunityService communityService) {
        this.userService = userService;
        this.subscriptionService = subscriptionService;
        this.communityService = communityService;
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addUser(@Valid @RequestBody UserRegistrationForm userForm,
                                  BindingResult result,
                                  UriComponentsBuilder uriComponentsBuilder) {
        new UserValidator(userService).validate(userForm, result);
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            User user = userForm.createUser();
            userService.registerNewUser(user);
            URI location = uriComponentsBuilder.path("/{username}").buildAndExpand(user.getUsername()).toUri();
            return ResponseEntity.created(location).build();
        }
    }

    @PostMapping(value = "/check_username")
    public String checkUsername(String username) {
        return userService.checkUsername(username).toString();
    }

    @PostMapping(value = "/check_email")
    public String checkEmail(String email) {
        return userService.checkEmail(email).toString();
    }

    @PostMapping(value = "/get_subscribed_users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Subscription>> getSubscribedUsers(@RequestParam(value = "communityTitle") String communityTitle) {
        Community community = communityService.getByTitle(communityTitle);
        List<Subscription> subscriptions = subscriptionService.getByCommunity(community);
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }

    @GetMapping(value = "/check_authorization")
    public ResponseEntity getUser(Principal principal) {
        if (principal != null) {
            return ResponseEntity.ok(principal.getName());
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}

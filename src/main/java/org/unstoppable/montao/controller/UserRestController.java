package org.unstoppable.montao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.unstoppable.montao.entity.Community;
import org.unstoppable.montao.entity.Subscription;
import org.unstoppable.montao.entity.User;
import org.unstoppable.montao.exception.CommunityNotFoundException;
import org.unstoppable.montao.exception.RegistrationFormException;
import org.unstoppable.montao.exception.UserNotAuthorizedException;
import org.unstoppable.montao.model.UserRegistrationForm;
import org.unstoppable.montao.service.CommunityService;
import org.unstoppable.montao.service.SubscriptionService;
import org.unstoppable.montao.service.UserService;
import org.unstoppable.montao.validator.UserValidator;

import javax.validation.Valid;
import java.math.BigInteger;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addUser(@Valid @RequestBody UserRegistrationForm userForm,
                                  BindingResult result,
                                  UriComponentsBuilder uriComponentsBuilder) {
        new UserValidator(userService).validate(userForm, result);
        if (result.hasErrors()) {
            throw new RegistrationFormException("Form validation failure");
        }
        User user = userForm.createUser();
        userService.registerNewUser(user);
        URI location = uriComponentsBuilder.path("/{username}").buildAndExpand(user.getUsername()).toUri();
        return ResponseEntity.created(location).build();
    }

    //todo подправить, только например админ мог получить весь список. Добавить ошибки и тд.
    //todo добивить лимит и оффсет
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getUsers() {
        List<User> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    //todo добавить ошибки
    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getUserById(@PathVariable("userId") String userId) {
        User user = userService.getById(BigInteger.valueOf(Long.parseLong(userId)));
        return ResponseEntity.ok(user);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteUser(User user) {
        userService.delete(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity updateUser(@PathVariable("userId") String userId) {
        //todo доделать
        return ResponseEntity.badRequest().build();
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
    public ResponseEntity getSubscribedUsers(@RequestParam(value = "communityTitle") String communityTitle) {
        Community community = communityService.getByTitle(communityTitle);
        if (community == null) {
            throw new CommunityNotFoundException("Community not found");
        }
        List<Subscription> subscriptions = subscriptionService.getByCommunity(community);
        return ResponseEntity.ok(subscriptions);
    }

    @GetMapping(value = "/check_authorization")
    public ResponseEntity getPrincipal(Principal principal) {
        if (principal == null) {
            throw new UserNotAuthorizedException("Not authorized");
        }
        return ResponseEntity.ok(principal.getName());
    }
}

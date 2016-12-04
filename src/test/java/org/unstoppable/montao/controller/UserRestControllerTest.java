package org.unstoppable.montao.controller;

import com.sun.security.auth.UserPrincipal;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriComponentsBuilder;
import org.unstoppable.montao.entity.Community;
import org.unstoppable.montao.entity.Subscription;
import org.unstoppable.montao.entity.User;
import org.unstoppable.montao.model.UserRegistrationForm;
import org.unstoppable.montao.service.CommunityService;
import org.unstoppable.montao.service.SubscriptionService;
import org.unstoppable.montao.service.UserService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserRestControllerTest {
    private MockMvc mockMvc;
    private UserService userService;
    private SubscriptionService subscriptionService;
    private CommunityService communityService;

    @Before
    public void setUp() throws Exception {
        userService = Mockito.mock(UserService.class);
        subscriptionService = Mockito.mock(SubscriptionService.class);
        communityService = Mockito.mock(CommunityService.class);
        UserRestController controller = new UserRestController(userService, subscriptionService, communityService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void addUser() throws Exception {
        UserRegistrationForm userForm = Helper.createUserForm();
        Mockito.when(userService.checkUsername(userForm.getUsername())).thenReturn(true);
        Mockito.when(userService.checkEmail(userForm.getEmail())).thenReturn(true);
        RequestBuilder request = post("/api/user/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(Helper.json(userForm));
        String uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .path("/{username}")
                .buildAndExpand(userForm.getUsername())
                .toUriString();
        ResultMatcher created = status().isCreated();
        ResultMatcher location = header().string("location", uri);
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(created)
                .andExpect(location);
    }

    @Test
    public void addWrongUser() throws Exception {
        UserRegistrationForm userForm = Helper.createUserForm();
        userForm.setEmail("sdfsd");
        userForm.setUsername("ывпаыапва");
        RequestBuilder request = post("/api/user/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(Helper.json(userForm));
        ResultMatcher conflict = status().isConflict();
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(conflict);
    }

    @Test
    public void checkExistUsername() throws Exception {
        UserRegistrationForm user = Helper.createUserForm();
        Mockito.when(userService.checkUsername(user.getUsername())).thenReturn(false);
        ResultMatcher ok = status().isOk();
        ResultMatcher result = content().string("false");
        mockMvc.perform(
                post("/api/user/check_username")
                        .param("username", user.getUsername()))
                .andDo(print())
                .andExpect(ok)
                .andExpect(result);
    }

    @Test
    public void checkNewUsername() throws Exception {
        Mockito.when(userService.checkUsername("new_user")).thenReturn(true);
        RequestBuilder request = post("/api/user/check_username")
                .param("username", "new_user");
        ResultMatcher ok = status().isOk();
        ResultMatcher result = content().string("true");
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(ok)
                .andExpect(result);
    }

    @Test
    public void checkExistEmail() throws Exception {
        UserRegistrationForm user = Helper.createUserForm();
        Mockito.when(userService.checkEmail(user.getEmail())).thenReturn(false);
        RequestBuilder request = post("/api/user/check_email")
                .param("email", user.getEmail());
        ResultMatcher result = content().string("false");
        ResultMatcher ok = status().isOk();
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(ok)
                .andExpect(result);
    }

    @Test
    public void checkNewEmail() throws Exception {
        Mockito.when(userService.checkEmail("newemail@mail.com")).thenReturn(true);
        ResultMatcher ok = status().isOk();
        ResultMatcher result = content().string("true");
        RequestBuilder request = post("/api/user/check_email")
                .param("email", "newemail@mail.com");
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(ok)
                .andExpect(result);
    }

    @Test
    public void getPrincipal() throws Exception {
        String username = "username";
        RequestBuilder request = get("/api/user/check_authorization")
                .principal(new UserPrincipal(username));
        ResultMatcher ok = status().isOk();
        ResultMatcher result = content().string(username);
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(ok)
                .andExpect(result);
    }

    @Test
    public void getNonAuthorizedPrincipal() throws Exception {
        RequestBuilder request = get("/api/user/check_authorization");
        ResultMatcher conflict = status().isConflict();
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(conflict);
    }

    @Test
    public void getSubscribedUsers() throws Exception {
        Community community = Helper.createCommunity();
        User user = Helper.createUser();
        Subscription subscription = Helper.createSubscription(community, user);
        List<Subscription> subscriptions = Helper.createSubscriptionList(subscription);
        Mockito.when(communityService.getByTitle(community.getTitle())).thenReturn(community);
        Mockito.when(subscriptionService.getByCommunity(community)).thenReturn(subscriptions);
        RequestBuilder request = post("/api/user/get_subscribed_users")
                .param("communityTitle", community.getTitle());
        ResultMatcher ok = status().isOk();
        ResultMatcher contentType = content().contentType(MediaType.APPLICATION_JSON_UTF8);
        String channelTitle = subscription.getCommunity().getChannels().get(0).getTitle();
        String communityTitle = subscription.getCommunity().getTitle();
        String username = subscription.getUser().getUsername();
        String email = subscription.getUser().getEmail();
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(ok)
                .andExpect(contentType)
                .andExpect(jsonPath("$[0].community.title").value(communityTitle))
                .andExpect(jsonPath("$[0].community.channels[0].title").value(channelTitle))
                .andExpect(jsonPath("$[0].user.username").value(username))
                .andExpect(jsonPath("$[0].user.email").value(email));
    }

    @Test
    public void getSubscribedUsersWithWrongCommunity() throws Exception {
        Community community = Helper.createCommunity();
        Mockito.when(communityService.getByTitle(community.getTitle())).thenReturn(null);
        RequestBuilder request = post("/api/user/get_subscribed_users")
                .param("communityTitle", community.getTitle());
        ResultMatcher noContent = status().isNoContent();
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(noContent);
    }
}
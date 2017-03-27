package org.unstoppable.montao.controller;

import com.sun.security.auth.UserPrincipal;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriComponentsBuilder;
import org.unstoppable.montao.entity.Community;
import org.unstoppable.montao.entity.Subscription;
import org.unstoppable.montao.entity.User;
import org.unstoppable.montao.model.CommunityCreationForm;
import org.unstoppable.montao.model.CommunitySubscription;
import org.unstoppable.montao.service.ChannelService;
import org.unstoppable.montao.service.CommunityService;
import org.unstoppable.montao.service.SubscriptionService;
import org.unstoppable.montao.service.UserService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CommunityRestControllerTest {
    private static final String GET_ALL = "/api/communities/get_all";
    private static final String ADD = "/api/communities";
    private static final String CHECK_TITLE = "/api/communities/check_title";
    private static final String JOIN = "/api/communities/join";
    private static final String LEAVE = "/api/communities/leave";
    private static final String CHECK_SUBSCRIPTION = "/api/communities/check_subscription";


    private CommunityService communityService;
    private UserService userService;
    private SubscriptionService subscriptionService;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        communityService = Mockito.mock(CommunityService.class);
        userService = Mockito.mock(UserService.class);
        ChannelService channelService = Mockito.mock(ChannelService.class);
        subscriptionService = Mockito.mock(SubscriptionService.class);
        CommunityRestController controller = new CommunityRestController(
                communityService,
                userService,
                channelService,
                subscriptionService);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new ExceptionHandlerController())
                .build();
    }

    @Test
    public void addCommunity() throws Exception {
        Community community = Helper.createCommunity();
        User user = Helper.createUser();
        CommunityCreationForm communityForm = Helper.createCommunityForm(community, user);
        Mockito.when(communityService.checkTitle(communityForm.getTitle())).thenReturn(true);
        Mockito.when(userService.getByUsername(user.getUsername())).thenReturn(user);
        String uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .path("/communities/{communityTitle}")
                .buildAndExpand(community.getTitle())
                .toUriString();
        RequestBuilder request = post(ADD)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(Helper.json(communityForm));
        ResultMatcher created = status().isCreated();
        ResultMatcher location = header().string("location", uri);
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(created)
                .andExpect(location);
    }

    @Test
    public void addAlreadyExistedCommunity() throws Exception {
        Community community = Helper.createCommunity();
        User user = Helper.createUser();
        CommunityCreationForm communityForm = Helper.createCommunityForm(community, user);
        Mockito.when(communityService.checkTitle(communityForm.getTitle())).thenReturn(false);
        RequestBuilder request = post(ADD)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(Helper.json(communityForm));
        ResultMatcher conflict = status().isConflict();
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(conflict)
                .andExpect(jsonPath("$.code").value(HttpStatus.CONFLICT.value()))
                .andExpect(jsonPath("$.message").value("Form validation failed"));
    }

    @Test
    public void checkExistTitle() throws Exception {
        Community community = Helper.createCommunity();
        User user = Helper.createUser();
        CommunityCreationForm communityForm = Helper.createCommunityForm(community, user);
        Mockito.when(communityService.checkTitle(communityForm.getTitle())).thenReturn(false);
        ResultMatcher ok = status().isOk();
        ResultMatcher result = content().string("false");
        mockMvc.perform(
                post(CHECK_TITLE)
                        .param("communityTitle", communityForm.getTitle()))
                .andDo(print())
                .andExpect(ok)
                .andExpect(result);
    }

    @Test
    public void checkNewTitle() throws Exception {
        Community community = Helper.createCommunity();
        User user = Helper.createUser();
        CommunityCreationForm communityForm = Helper.createCommunityForm(community, user);
        Mockito.when(communityService.checkTitle(communityForm.getTitle())).thenReturn(true);
        RequestBuilder request = post(CHECK_TITLE)
                .param("communityTitle", communityForm.getTitle());
        ResultMatcher ok = status().isOk();
        ResultMatcher result = content().string("true");
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(ok)
                .andExpect(result);
    }

    @Test
    public void getCommunitiesWithPrincipal() throws Exception {
        User user = Helper.createUser();
        Community community = Helper.createCommunity();
        CommunitySubscription communitySubscription = Helper.createCommunitySubscription(community);
        List<CommunitySubscription> communitySubscriptions = Helper.createCommunitySubscriptionList(communitySubscription);

        Mockito.when(userService.getByUsername(user.getUsername())).thenReturn(user);
        Mockito.when(subscriptionService.getCommunitiesWithSubscriptionsByUser(user, 0, 40))
                .thenReturn(communitySubscriptions);
        RequestBuilder request = post(GET_ALL)
                .param("startRowPosition", "0")
                .principal(new UserPrincipal(user.getUsername()));
        ResultMatcher ok = status().isOk();
        ResultMatcher contentType = content().contentType(MediaType.APPLICATION_JSON_UTF8);
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(ok)
                .andExpect(contentType)
                .andExpect(jsonPath("$[0].title").value(communitySubscriptions.get(0).getTitle()))
                .andExpect(jsonPath("$[0].subscribed").value(communitySubscriptions.get(0).getSubscribed()));
    }

    @Test
    public void getCommunitiesWhenNotAuthorized() throws Exception {
        Community community = Helper.createCommunity();
        List<Community> communityList = Helper.createCommunityList(community);
        CommunitySubscription communitySubscription = Helper.createCommunitySubscription(community);
        List<CommunitySubscription> communitySubscriptions = Helper.createCommunitySubscriptionList(communitySubscription);

        Mockito.when(communityService.getPublicCommunities(0, 40)).thenReturn(communityList);

        RequestBuilder request = post(GET_ALL)
                .param("startRowPosition", "0");
        ResultMatcher ok = status().isOk();
        ResultMatcher contentType = content().contentType(MediaType.APPLICATION_JSON_UTF8);
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(ok)
                .andExpect(contentType)
                .andExpect(jsonPath("$[0].title").value(communitySubscriptions.get(0).getTitle()))
                .andExpect(jsonPath("$[0].subscribed").value(false));
    }

    @Test
    public void join() throws Exception {
        Community community = Helper.createCommunity();
        CommunitySubscription communitySubscription = Helper.createCommunitySubscription(community);
        User user = Helper.createUser();
        Mockito.when(communityService.getByTitle(community.getTitle())).thenReturn(community);
        Mockito.when(userService.getByUsername(user.getUsername())).thenReturn(user);
        RequestBuilder request = post(JOIN)
                .param("communityTitle", community.getTitle())
                .principal(new UserPrincipal(user.getUsername()));
        ResultMatcher ok = status().isOk();
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(ok)
                .andExpect(jsonPath("$.title").value(communitySubscription.getTitle()))
                .andExpect(jsonPath("subscribed").value(communitySubscription.getSubscribed()));
    }

    @Test
    public void joinNonAuthorized() throws Exception {
        testNonAuthorized(JOIN);
    }

    @Test
    public void unsubscribe() throws Exception {
        Community community = Helper.createCommunity();
        User user = Helper.createUser();
        Subscription subscription = Helper.createSubscription(community, user);

        Mockito.when(communityService.getByTitle(community.getTitle())).thenReturn(community);
        Mockito.when(userService.getByUsername(user.getUsername())).thenReturn(user);
        Mockito.when(subscriptionService.get(community, user)).thenReturn(subscription);
        RequestBuilder request = post(LEAVE)
                .param("communityTitle", community.getTitle())
                .principal(new UserPrincipal(user.getUsername()));
        ResultMatcher ok = status().isOk();
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(ok)
                .andExpect(jsonPath("$.title").value(community.getTitle()))
                .andExpect(jsonPath("$.subscribed").value(false));
    }

    @Test
    public void unsubscribeNonAuthorized() throws Exception {
        testNonAuthorized(LEAVE);
    }

    @Test
    public void testUnsubscribeWhenSubscriptionNotFound() throws Exception {
        Community community = Helper.createCommunity();
        User user = Helper.createUser();

        Mockito.when(communityService.getByTitle(community.getTitle())).thenReturn(community);
        Mockito.when(userService.getByUsername(user.getUsername())).thenReturn(user);
        Mockito.when(subscriptionService.get(community, user)).thenReturn(null);
        RequestBuilder request = post(LEAVE)
                .param("communityTitle", community.getTitle())
                .principal(new UserPrincipal(user.getUsername()));
        ResultMatcher badRequest = status().isBadRequest();
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(badRequest)
                .andExpect(content().string("Subscription not found"));
    }

    @Test
    public void checkSubscriptionWhenSubscribed() throws Exception {
        Community community = Helper.createCommunity();
        User user = Helper.createUser();
        Mockito.when(userService.getByUsername(user.getUsername())).thenReturn(user);
        Mockito.when(communityService.getByTitle(community.getTitle())).thenReturn(community);
        Mockito.when(subscriptionService.checkSubscription(community, user)).thenReturn(true);
        RequestBuilder request = post(CHECK_SUBSCRIPTION)
                .param("communityTitle", community.getTitle())
                .principal(new UserPrincipal(user.getUsername()));
        ResultMatcher ok = status().isOk();
        ResultMatcher result = content().string("true");
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(ok)
                .andExpect(result);
    }

    @Test
    public void checkSubscriptionWhenNotSubdcribed() throws Exception {
        Community community = Helper.createCommunity();
        User user = Helper.createUser();
        Mockito.when(userService.getByUsername(user.getUsername())).thenReturn(user);
        Mockito.when(communityService.getByTitle(community.getTitle())).thenReturn(community);
        Mockito.when(subscriptionService.checkSubscription(community, user)).thenReturn(false);
        RequestBuilder request = post(CHECK_SUBSCRIPTION)
                .param("communityTitle", community.getTitle())
                .principal(new UserPrincipal(user.getUsername()));
        ResultMatcher ok = status().isOk();
        ResultMatcher result = content().string("false");
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(ok)
                .andExpect(result);
    }

    @Test
    public void checkSubscriptionNonAuthorized() throws Exception {
        Community community = Helper.createCommunity();
        RequestBuilder request = post(CHECK_SUBSCRIPTION)
                .param("communityTitle", community.getTitle());
        ResultMatcher ok = status().isOk();
        ResultMatcher result = content().string("false");
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(ok)
                .andExpect(result);
    }

    private void testNonAuthorized(String path) throws Exception {
        Community community = Helper.createCommunity();

        RequestBuilder request = post(path)
                .param("communityTitle", community.getTitle());
        ResultMatcher notAllowed = status().isMethodNotAllowed();
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(notAllowed)
                .andExpect(jsonPath("$.code").value(HttpStatus.METHOD_NOT_ALLOWED.value()))
                .andExpect(jsonPath("$.message").value("Allows to authorized users only"));
    }
}

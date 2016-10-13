package org.unstoppable.projectstack.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.unstoppable.projectstack.entity.Channel;
import org.unstoppable.projectstack.entity.Community;
import org.unstoppable.projectstack.entity.User;
import org.unstoppable.projectstack.service.CommunityService;
import org.unstoppable.projectstack.service.SubscriptionService;
import org.unstoppable.projectstack.service.UserService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProfileControllerTest {
    private MockMvc mockMvc;
    private UserService userService;
    private CommunityService communityService;

    @Before
    public void setUp() throws Exception {
        userService = Mockito.mock(UserService.class);
        communityService = Mockito.mock(CommunityService.class);
        SubscriptionService subscriptionService = Mockito.mock(SubscriptionService.class);
        ProfileController controller = new ProfileController(userService, communityService, subscriptionService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setViewResolvers(getViewResolver()).build();
    }

    private ViewResolver getViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/templates/");
        viewResolver.setSuffix(".html");
        return viewResolver;
    }

    @Test
    public void getUserProfileByUsername() throws Exception {
        User user = createUser();
        Mockito.when(userService.getByUsername("username666")).thenReturn(user);
        RequestBuilder request = get("/username666");
        mockMvc.perform(request)
                .andExpect(view().name("profile"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", user));
    }

    @Test
    public void getUserProfileByWrongUsername() throws Exception {
        mockMvc.perform(get("/username666"))
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void getUserProfileById() throws Exception {
        User user = createUser();
        Mockito.when(userService.getById(BigInteger.valueOf(666))).thenReturn(user);
        RequestBuilder request = get("/id666");
        mockMvc.perform(request)
                .andExpect(view().name("profile"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", user));
    }

    @Test
    public void getUserProfileByWrongId() throws Exception {
        mockMvc.perform(get("/id666"))
                .andExpect(redirectedUrl("/"));
    }

    private User createUser() {
        User user = new User();
        user.setUsername("username666");
        user.setPassword("password");
        user.setEmail("email@mail.com");
        return user;
    }

    @Test
    public void getCommunity() throws Exception {
        Community community = createCommunity();
        Mockito.when(communityService.getByTitle("communityTest")).thenReturn(community);
        RequestBuilder request = get("/communityTest");
        mockMvc.perform(request)
                .andExpect(view().name("community"));
    }

    private Channel createChannel(Community community) {
        Channel channel = new Channel();
        channel.setTitle("channelTest");
        channel.setCommunity(community);
        return channel;
    }

    private Community createCommunity() {
        Community community = new Community();
        community.setTitle("communityTest");
        community.setVisible(true);
        List<Channel> channels = new ArrayList<>();
        Channel channel = createChannel(community);
        channels.add(channel);
        community.setChannels(channels);
        return community;
    }
}
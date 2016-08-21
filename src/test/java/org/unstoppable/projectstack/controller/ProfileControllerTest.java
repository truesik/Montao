package org.unstoppable.projectstack.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.unstoppable.projectstack.entity.User;
import org.unstoppable.projectstack.service.CommunityService;
import org.unstoppable.projectstack.service.UserService;

import java.math.BigInteger;

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
        ProfileController controller = new ProfileController(userService, communityService);
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

}
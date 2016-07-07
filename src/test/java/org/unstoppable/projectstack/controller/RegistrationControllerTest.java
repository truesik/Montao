package org.unstoppable.projectstack.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.unstoppable.projectstack.entity.User;
import org.unstoppable.projectstack.service.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RegistrationControllerTest {
    private MockMvc mockMvc;
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        userService = Mockito.mock(UserService.class);
        RegistrationController controller = new RegistrationController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setViewResolvers(getViewResolver()).build();
    }

    private ViewResolver getViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/templates/");
        viewResolver.setSuffix(".html");
        return viewResolver;
    }

    @Test
    public void registration() throws Exception {
        mockMvc.perform(get("/registration")).andExpect(view().name("registration"));
    }

    @Test
    public void addUser() throws Exception {
        User user = createUser();
        RequestBuilder request = post("/registration")
                .param("username", user.getUsername())
                .param("password", user.getPassword())
                .param("email", user.getEmail());
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(redirectedUrl("/success"));
        Mockito.verify(userService, Mockito.atLeastOnce()).add(user);
    }

    private User createUser() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email@mail.com");
        return user;
    }

    @Test
    public void addWrongUser() throws Exception {
        User user = createUser();
        user.setEmail("sdfsd");

        RequestBuilder request = post("/registration")
                .param("username", user.getUsername())
                .param("password", user.getEmail())
                .param("email", user.getEmail());
        ResultMatcher result = view().name("registration");
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(result);
    }

    @Test
    public void checkExistUsername() throws Exception {
        User user = createUser();
        Mockito.when(userService.checkUsername(user.getUsername())).thenReturn(false);
        mockMvc.perform(
                post("/registration/check_username")
                        .param("username", user.getUsername()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    public void checkNewUsername() throws Exception {
        Mockito.when(userService.checkUsername("new_user")).thenReturn(true);
        mockMvc.perform(
                post("/registration/check_username")
                        .param("username", "new_user"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void checkExistEmail() throws Exception {
        User user = createUser();
        Mockito.when(userService.checkEmail(user.getEmail())).thenReturn(false);
        mockMvc.perform(
                post("/registration/check_email")
                        .param("email", user.getEmail()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    public void checkNewEmail() throws Exception {
        Mockito.when(userService.checkEmail("newemail@mail.com")).thenReturn(true);
        mockMvc.perform(
                post("/registration/check_email")
                        .param("email", "newemail@mail.com"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

}
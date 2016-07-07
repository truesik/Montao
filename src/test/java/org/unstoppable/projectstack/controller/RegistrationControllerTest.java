package org.unstoppable.projectstack.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.unstoppable.projectstack.entity.User;
import org.unstoppable.projectstack.service.UserService;

import java.math.BigInteger;
import java.time.LocalDate;

import static org.junit.Assert.*;
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
        mockMvc.perform(
                post("/registration")
                        .param("username", "username")
                        .param("password", "password")
                        .param("email", "email@mail.com")
                        .param("registrationDate", String.valueOf(LocalDate.now())))
                .andExpect(redirectedUrl("/success"));
        Mockito.verify(userService, Mockito.atLeastOnce()).add(getUser());
    }

    private User getUser() {
        User user = new User();
        user.setUsername("user");
        user.setPassword("password");
        user.setEmail("email@mail.com");
        user.setRegistrationDate(LocalDate.now());
        return user;
    }

    @Test
    public void checkUsername() throws Exception {

    }

    @Test
    public void checkEmail() throws Exception {

    }

}
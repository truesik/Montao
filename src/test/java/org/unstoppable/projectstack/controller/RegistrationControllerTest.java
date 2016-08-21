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
import org.unstoppable.projectstack.model.UserRegistrationForm;
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
        UserRegistrationForm userForm = createUserForm();
        Mockito.when(userService.checkUsername(userForm.getUsername())).thenReturn(true);
        Mockito.when(userService.checkEmail(userForm.getEmail())).thenReturn(true);
        RequestBuilder request = post("/registration")
                .param("username", userForm.getUsername())
                .param("password", userForm.getPassword())
                .param("confirmPassword", userForm.getConfirmPassword())
                .param("email", userForm.getEmail());
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(redirectedUrl("/success"));
//        Mockito.verify(userService, Mockito.atLeastOnce()).registerNewUser(userForm.createUser());
    }

    private UserRegistrationForm createUserForm() {
        UserRegistrationForm user = new UserRegistrationForm();
        user.setUsername("username");
        user.setPassword("password");
        user.setConfirmPassword("password");
        user.setEmail("email@mail.com");
        return user;
    }

    @Test
    public void addWrongUser() throws Exception {
        UserRegistrationForm user = createUserForm();
        user.setEmail("sdfsd");
        user.setUsername("ывпаыапва");

        RequestBuilder request = post("/registration")
                .param("username", user.getUsername())
                .param("password", user.getEmail())
                .param("confirmPassword", user.getConfirmPassword())
                .param("email", user.getEmail());
        ResultMatcher result = view().name("registration");
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(result);
    }

    @Test
    public void checkExistUsername() throws Exception {
        UserRegistrationForm user = createUserForm();
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
        UserRegistrationForm user = createUserForm();
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

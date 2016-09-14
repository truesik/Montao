package org.unstoppable.projectstack.controller;

import org.junit.Before;
import org.mockito.Mockito;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.unstoppable.projectstack.service.*;

import static org.junit.Assert.*;

public class ChannelControllerTest {
    private CommunityService communityService;
    private ChannelService channelService;
    private UserService userService;
    private MessageService messageService;
    private SubscriptionService subscriptionService;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        communityService = Mockito.mock(CommunityService.class);
        channelService = Mockito.mock(ChannelService.class);
        userService = Mockito.mock(UserService.class);
        messageService = Mockito.mock(MessageService.class);
        SimpMessagingTemplate simpMessagingTemplate = Mockito.mock(SimpMessagingTemplate.class);
        subscriptionService = Mockito.mock(SubscriptionService.class);
        ChannelController controller = new ChannelController(
                communityService,
                channelService,
                messageService,
                userService,
                simpMessagingTemplate,
                subscriptionService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setViewResolvers(getViewResolver()).build();
    }

    private ViewResolver getViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/templates/");
        viewResolver.setSuffix(".html");
        return viewResolver;
    }

}
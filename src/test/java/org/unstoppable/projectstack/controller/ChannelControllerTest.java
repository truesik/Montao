package org.unstoppable.projectstack.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.unstoppable.projectstack.entity.Channel;
import org.unstoppable.projectstack.entity.Community;
import org.unstoppable.projectstack.model.ChannelCreationForm;
import org.unstoppable.projectstack.service.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Test
    public void addChannel() throws Exception {
        Community community = createCommunity();
        ChannelCreationForm channelForm = createChannelForm();
        Mockito.when(channelService.checkTitle(channelForm.getTitle(), community.getTitle())).thenReturn(true);
        RequestBuilder request = post("/" + community.getTitle() + "/channels/new")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json(channelForm));
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().string("success"))
                .andExpect(status().isOk());
    }

    @Test
    public void addWrongChannel() throws Exception {
        Community community = createCommunity();
        ChannelCreationForm channelForm = createChannelForm();
        Mockito.when(channelService.checkTitle(channelForm.getTitle(), community.getTitle())).thenReturn(false);
        RequestBuilder request = post("/" + community.getTitle() + "/channels/new")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json(channelForm));
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().string("failure"))
                .andExpect(status().isOk());
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

    private Channel createChannel(Community community) {
        Channel channel = new Channel();
        channel.setTitle("channelTest");
        channel.setCommunity(community);
        return channel;
    }

    private ChannelCreationForm createChannelForm() {
        ChannelCreationForm channelForm = new ChannelCreationForm();
        channelForm.setTitle("channelTest");
        channelForm.setDescription("descriptionTest");
        return channelForm;
    }

    protected byte[] json(Object o) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(o);
    }
}
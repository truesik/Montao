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
import org.unstoppable.projectstack.entity.Message;
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

    @Test
    public void checkTitle() throws Exception {
        String COMMUNITY_TITLE = "communityTest";
        String CHANNEL_TITLE = "channelTest";
        Mockito.when(channelService.checkTitle("channelTest", COMMUNITY_TITLE)).thenReturn(true);
        RequestBuilder request = post("/" + COMMUNITY_TITLE + "/channels/check_title").param("title", CHANNEL_TITLE);
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().string("true"))
                .andExpect(status().isOk());
    }

    @Test
    public void channel() throws Exception {

    }

    @Test
    public void getMessages() throws Exception {
        Community community = createCommunity();
        Channel channel = community.getChannels().get(0);
        Message message = createMessage(channel);
        List<Message> messages = new ArrayList<>();
        messages.add(message);

        Mockito.when(communityService.getByTitle(community.getTitle())).thenReturn(community);
        Mockito.when(messageService.getByChannelWithLimitation(channel, 0, 20)).thenReturn(messages);
        RequestBuilder request = post("/" + community.getTitle() + "/channels/" + channel.getTitle() + "/messages").contentType(MediaType.APPLICATION_JSON_VALUE).param("startRowPosition", "0");
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$[0].message").value(message.getMessage()));
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

    private Message createMessage(Channel channel) {
        Message message = new Message();
        message.setMessage("Hello");
        message.setChannel(channel);
        return message;
    }

    protected byte[] json(Object o) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(o);
    }
}
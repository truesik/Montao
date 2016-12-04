package org.unstoppable.montao.controller;

import com.sun.security.auth.UserPrincipal;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.unstoppable.montao.entity.Channel;
import org.unstoppable.montao.entity.Community;
import org.unstoppable.montao.entity.Message;
import org.unstoppable.montao.entity.User;
import org.unstoppable.montao.service.CommunityService;
import org.unstoppable.montao.service.MessageService;
import org.unstoppable.montao.service.UserService;


import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MessageRestControllerTest {
    private CommunityService communityService;
    private MessageService messageService;
    private UserService userService;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        communityService = Mockito.mock(CommunityService.class);
        messageService = Mockito.mock(MessageService.class);
        userService = Mockito.mock(UserService.class);
        SimpMessagingTemplate messagingTemplate = Mockito.mock(SimpMessagingTemplate.class);
        MessageRestController controller = new MessageRestController(
                communityService,
                userService,
                messageService,
                messagingTemplate);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void addMessageWithPrincipal() throws Exception {
        User user = Helper.createUser();
        Community community = Helper.createCommunity();
        Channel channel = Helper.createChannel(community);
        Message message = Helper.createMessage(channel);
        Mockito.when(communityService.getByTitle(community.getTitle())).thenReturn(community);
        Mockito.when(userService.getByUsername(user.getUsername())).thenReturn(user);
        RequestBuilder request = post("/api/message/add")
                .principal(new UserPrincipal(user.getUsername()))
                .param("text", message.getText())
                .param("communityTitle", community.getTitle())
                .param("channelTitle", channel.getTitle());
        ResultMatcher created = status().isCreated();
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(created);
    }

    @Test
    public void addMessage() throws Exception {
        Community community = Helper.createCommunity();
        Channel channel = Helper.createChannel(community);
        Message message = Helper.createMessage(channel);
        RequestBuilder request = post("/api/message/add")
                .param("text", message.getText())
                .param("communityTitle", community.getTitle())
                .param("channelTitle", channel.getTitle());
        ResultMatcher notAllowed = status().isMethodNotAllowed();
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(notAllowed);
    }

    @Test
    public void getMessages() throws Exception {
        Community community = Helper.createCommunity();
        Channel channel = Helper.createChannel(community);
        Message message = Helper.createMessage(channel);
        List<Message> messages = Helper.createMessageList(message);
        Mockito.when(communityService.getByTitle(community.getTitle())).thenReturn(community);
        Mockito.when(messageService.getByChannelWithLimitation(channel, 0, 20)).thenReturn(messages);
        RequestBuilder request = post("/api/message/get_messages")
                .param("communityTitle", community.getTitle())
                .param("channelTitle", channel.getTitle())
                .param("startRowPosition", "0");
        ResultMatcher ok = status().isOk();
        ResultMatcher contentType = content().contentType(MediaType.APPLICATION_JSON_UTF8);
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(ok)
                .andExpect(contentType)
                .andExpect(jsonPath("$[0].text").value(message.getText()))
                .andExpect(jsonPath("$[0].channel.title").value(channel.getTitle()));
    }

    @Test
    public void getMessagesWhenCurrentChannelIsNull() throws Exception {
        Community community = Helper.createCommunity();
        Mockito.when(communityService.getByTitle(community.getTitle())).thenReturn(community);
        RequestBuilder request = post("/api/message/get_messages")
                .param("communityTitle", community.getTitle())
                .param("channelTitle", "fake")
                .param("startRowPosition", "0");
        ResultMatcher noContent = status().isNoContent();
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(noContent);
    }
}
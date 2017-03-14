package org.unstoppable.montao.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriComponentsBuilder;
import org.unstoppable.montao.entity.Channel;
import org.unstoppable.montao.entity.Community;
import org.unstoppable.montao.model.ChannelCreationForm;
import org.unstoppable.montao.service.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ChannelRestControllerTest {
    private MockMvc mockMvc;
    private CommunityService communityService;
    private ChannelService channelService;

    @Before
    public void setUp() throws Exception {
        communityService = Mockito.mock(CommunityService.class);
        channelService = Mockito.mock(ChannelService.class);
        SimpMessagingTemplate simpMessagingTemplate = Mockito.mock(SimpMessagingTemplate.class);
        ChannelRestController controller = new ChannelRestController(
                communityService,
                channelService,
                simpMessagingTemplate);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new ExceptionHandlerController())
                .build();
    }

    @Test
    public void addChannel() throws Exception {
        ChannelCreationForm channelForm = Helper.createChannelForm();
        Community community = Helper.createCommunity();
        Mockito.when(channelService.checkTitle(channelForm.getTitle(), channelForm.getCommunityTitle())).thenReturn(true);
        Mockito.when(communityService.getByTitle(channelForm.getCommunityTitle())).thenReturn(community);
        String uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .path("/communities/{communityTitle}/channels/{channelTitle}")
                .buildAndExpand(community.getTitle(), channelForm.getTitle())
                .toUriString();
        RequestBuilder request = post("/api/channels")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(Helper.json(channelForm));
        ResultMatcher created = status().isCreated();
        ResultMatcher location = header().string("location", uri);
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(created)
                .andExpect(location);
    }

    @Test
    public void addAlreadyExistedChannel() throws Exception {
        ChannelCreationForm channelForm = Helper.createChannelForm();
        Mockito.when(channelService.checkTitle(channelForm.getTitle(), channelForm.getCommunityTitle())).thenReturn(false);
        RequestBuilder request = post("/api/channels")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(Helper.json(channelForm));
        ResultMatcher conflict = status().isConflict();
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(conflict);
    }

    @Test
    public void checkNotExistedTitle() throws Exception {
        Community community = Helper.createCommunity();
        Channel channel = Helper.createChannel(community);
        Mockito.when(channelService.checkTitle(channel.getTitle(), community.getTitle())).thenReturn(true);
        RequestBuilder request = post("/api/channels/check_title")
                .param("channelTitle", channel.getTitle())
                .param("communityTitle", community.getTitle());
        ResultMatcher ok = status().isOk();
        ResultMatcher result = content().string("true");
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(ok)
                .andExpect(result);
    }

    @Test
    public void checkExistedTitle() throws Exception {
        Community community = Helper.createCommunity();
        Channel channel = Helper.createChannel(community);
        Mockito.when(channelService.checkTitle(channel.getTitle(), community.getTitle())).thenReturn(false);
        RequestBuilder request = post("/api/channels/check_title")
                .param("channelTitle", channel.getTitle())
                .param("communityTitle", community.getTitle());
        ResultMatcher ok = status().isOk();
        ResultMatcher result = content().string("false");
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(ok)
                .andExpect(result);
    }

    @Test
    public void getChannels() throws Exception {
        Community community = Helper.createCommunity();
        Channel channel = Helper.createChannel(community);
        Mockito.when(communityService.getByTitle(community.getTitle())).thenReturn(community);
        RequestBuilder request = post("/api/channels/get_channels")
                .param("communityTitle", community.getTitle());
        ResultMatcher ok = status().isOk();
        String channelTitle = channel.getTitle();
        ResultMatcher contentType = content().contentType(MediaType.APPLICATION_JSON_UTF8);
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(ok)
                .andExpect(contentType)
                .andExpect(jsonPath("$[0].title").value(channelTitle));
    }

    @Test
    public void getEmptyChannelList() throws Exception {
        Community community = Helper.createCommunity();
        community.getChannels().clear();
        Mockito.when(communityService.getByTitle(community.getTitle())).thenReturn(community);
        RequestBuilder request = post("/api/channels/get_channels")
                .param("communityTitle", community.getTitle());
        ResultMatcher noContent = status().isNoContent();
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(noContent);
    }
}

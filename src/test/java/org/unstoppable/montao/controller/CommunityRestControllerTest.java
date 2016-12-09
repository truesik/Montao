package org.unstoppable.montao.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.unstoppable.montao.entity.Community;
import org.unstoppable.montao.entity.User;
import org.unstoppable.montao.model.CommunityCreationForm;
import org.unstoppable.montao.service.ChannelService;
import org.unstoppable.montao.service.CommunityService;
import org.unstoppable.montao.service.SubscriptionService;
import org.unstoppable.montao.service.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CommunityRestControllerTest {

    private CommunityService communityService;
    private UserService userService;
    private ChannelService channelService;
    private SubscriptionService subscriptionService;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        communityService = Mockito.mock(CommunityService.class);
        userService = Mockito.mock(UserService.class);
        channelService = Mockito.mock(ChannelService.class);
        subscriptionService = Mockito.mock(SubscriptionService.class);
        CommunityRestController controller = new CommunityRestController(
                communityService,
                userService,
                channelService,
                subscriptionService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

//    @Test
//    public void addCommunity() throws Exception {
//        Community community = Helper.createCommunity();
//        User user = Helper.createUser();
//        CommunityCreationForm communityForm = Helper.createCommunityForm(community, user);
//        post("/api/community/add")
//                .content(Helper.json(communityForm));
//    }

}
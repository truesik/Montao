package org.unstoppable.projectstack.controller;

import com.sun.security.auth.UserPrincipal;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.format.Formatter;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.unstoppable.projectstack.entity.Channel;
import org.unstoppable.projectstack.entity.Community;
import org.unstoppable.projectstack.entity.Subscription;
import org.unstoppable.projectstack.entity.User;
import org.unstoppable.projectstack.model.CommunityCreationForm;
import org.unstoppable.projectstack.model.CommunitySubscription;
import org.unstoppable.projectstack.service.ChannelService;
import org.unstoppable.projectstack.service.CommunityService;
import org.unstoppable.projectstack.service.SubscriptionService;
import org.unstoppable.projectstack.service.UserService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CommunityControllerTest {
    private UserService userService;
    private CommunityService communityService;
    private SubscriptionService subscriptionService;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        userService = Mockito.mock(UserService.class);
        communityService = Mockito.mock(CommunityService.class);
        ChannelService channelService = Mockito.mock(ChannelService.class);
        subscriptionService = Mockito.mock(SubscriptionService.class);
        CommunityController controller = new CommunityController(userService,
                communityService,
                channelService,
                subscriptionService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setConversionService(getConversionService(userService))
                .setViewResolvers(getViewResolver())
                .build();
    }

    private FormattingConversionService getConversionService(UserService userService) {
        FormattingConversionService formattingConversionService = new FormattingConversionService();
        formattingConversionService.addFormatterForFieldType(User.class, new Formatter<User>() {
            @Override
            public User parse(String text, Locale locale) throws ParseException {
                return userService.getByUsername(text);
            }

            @Override
            public String print(User object, Locale locale) {
                return object != null ? object.getUsername() : "";
            }
        });
        return formattingConversionService;
    }

    private ViewResolver getViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/templates/");
        viewResolver.setSuffix(".html");
        return viewResolver;
    }

    @Test
    public void createNewCommunityPage() throws Exception {
        User user = createUser();
        Mockito.when(userService.getByUsername(user.getUsername())).thenReturn(user);
        RequestBuilder request = get("/communities/new")
                .principal(new UserPrincipal(user.getUsername()));
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(view().name("newcommunity"));
    }

    @Test
    public void createNewCommunity() throws Exception {
        Community community = createCommunity();
        User user = createUser();
        CommunityCreationForm communityCreationForm = createCommunityCreationForm(community, user);
        Mockito.when(communityService.checkTitle(community.getTitle())).thenReturn(true);
        Mockito.when(userService.getByUsername(user.getUsername())).thenReturn(user);
        RequestBuilder request = post("/communities/new")
                .principal(new UserPrincipal(user.getUsername()))
                .param("title", communityCreationForm.getTitle())
                .param("description", communityCreationForm.getDescription())
                .param("founder", communityCreationForm.getFounder())
                .param("visible", String.valueOf(communityCreationForm.getVisible()));
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(redirectedUrl("/" + community.getTitle()));
    }

    @Test
    public void tryToCreateWrongCommunity() throws Exception {
        Community community = createCommunity();
        User user = createUser();
        CommunityCreationForm communityCreationForm = createCommunityCreationForm(community, user);
        Mockito.when(communityService.checkTitle(community.getTitle())).thenReturn(true);
        Mockito.when(userService.getByUsername(user.getUsername())).thenReturn(user);
        RequestBuilder request = post("/communities/new")
                .principal(new UserPrincipal(user.getUsername()))
                .param("title", "")
                .param("description", communityCreationForm.getDescription())
                .param("founder", communityCreationForm.getFounder())
                .param("visible", String.valueOf(communityCreationForm.getVisible()));
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(view().name("newcommunity"));
    }

    @Test
    public void checkTitle() throws Exception {
        String COMMUNITY_TITLE = "testTitle";
        Mockito.when(communityService.checkTitle(COMMUNITY_TITLE)).thenReturn(false);
        RequestBuilder request = post("/communities/check_title").param("title", COMMUNITY_TITLE);
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().string("false"));
    }

    @Test
    public void getCommunitiesByPrincipal() throws Exception {
        Community community = createCommunity();
        User user = createUser();
        CommunitySubscription communitySubscription = createCommunitySubscription(community);
        List<CommunitySubscription> communitySubscriptions = new ArrayList<>();
        communitySubscriptions.add(communitySubscription);

        Mockito.when(userService.getByUsername(user.getUsername())).thenReturn(user);
        Mockito.when(subscriptionService.getCommunitiesWithSubscriptionsByUser(user, 0, 40))
                .thenReturn(communitySubscriptions);
        RequestBuilder request = post("/communities")
                .param("startRowPosition", "0")
                .principal(new UserPrincipal(user.getUsername()));
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$[0].title").value(communitySubscription.getTitle()))
                .andExpect(jsonPath("$[0].description").value(communitySubscription.getDescription()))
                .andExpect(jsonPath("$[0].subscribed").value(communitySubscription.getSubscribed()));
    }

    @Test
    public void getCommunitiesWithoutPrincipal() throws Exception {
        Community community = createCommunity();
        List<Community> communities = new ArrayList<>();
        communities.add(community);
        CommunitySubscription communitySubscription = createCommunitySubscription(community);

        Mockito.when(communityService.getPublicCommunities(0, 40)).thenReturn(communities);
        RequestBuilder request = post("/communities")
                .param("startRowPosition", "0");
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$[0].title").value(communitySubscription.getTitle()))
                .andExpect(jsonPath("$[0].description").value(communitySubscription.getDescription()))
                .andExpect(jsonPath("$[0].subscribed").value(false));
    }

    private CommunitySubscription createCommunitySubscription(Community community) {
        CommunitySubscription communitySubscription = new CommunitySubscription();
        communitySubscription.setTitle(community.getTitle());
        communitySubscription.setDescription(community.getDescription());
        communitySubscription.setSubscribed(true);
        return communitySubscription;
    }

    @Test
    public void subscribeSuccess() throws Exception {
        Community community = createCommunity();
        User user = createUser();
        Mockito.when(communityService.getByTitle(community.getTitle())).thenReturn(community);
        Mockito.when(userService.getByUsername(user.getUsername())).thenReturn(user);
        RequestBuilder request = post("/communities/subscribe")
                .param("communityTitle", community.getTitle())
                .principal(new UserPrincipal(user.getUsername()));
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().string("success"));
    }

    @Test
    public void subscribeFailure() throws Exception {
        Community community = createCommunity();
        RequestBuilder request = post("/communities/subscribe")
                .param("communityTitle", community.getTitle());
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().string("failure"));
    }

    @Test
    public void unsubscribeSuccess() throws Exception {
        Community community = createCommunity();
        User user = createUser();
        Subscription subscription = createSubscription(community, user);
        Mockito.when(communityService.getByTitle(community.getTitle())).thenReturn(community);
        Mockito.when(userService.getByUsername(user.getUsername())).thenReturn(user);
        Mockito.when(subscriptionService.get(community, user)).thenReturn(subscription);
        RequestBuilder request = post("/communities/unsubscribe")
                .param("communityTitle", community.getTitle())
                .principal(new UserPrincipal(user.getUsername()));
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().string("success"));
    }

    @Test
    public void unsubscribeFailure() throws Exception {
        Community community = createCommunity();
        RequestBuilder request = post("/communities/unsubscribe")
                .param("communityTitle", community.getTitle());
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().string("failure"));
    }

    private User createUser() {
        User user = new User();
        user.setUsername("userTest");
        user.setEmail("test@test.com");
        user.setPassword("passwordTest");
        return user;
    }

    private Channel createChannel(Community community) {
        Channel channel = new Channel();
        channel.setTitle("channelTest");
        channel.setCommunity(community);
        return channel;
    }

    private Subscription createSubscription(Community community, User user) {
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setCommunity(community);
        return subscription;
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

    private CommunityCreationForm createCommunityCreationForm(Community community, User user) {
        CommunityCreationForm communityCreationForm = new CommunityCreationForm();
        communityCreationForm.setTitle(community.getTitle());
        communityCreationForm.setDescription(community.getDescription());
        communityCreationForm.setFounder(user.getUsername());
        communityCreationForm.setVisible(true);
        return communityCreationForm;
    }
}
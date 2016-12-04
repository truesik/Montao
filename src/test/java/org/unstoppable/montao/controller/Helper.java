package org.unstoppable.montao.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.unstoppable.montao.entity.*;
import org.unstoppable.montao.model.ChannelCreationForm;
import org.unstoppable.montao.model.UserRegistrationForm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Helper {
    public static UserRegistrationForm createUserForm() {
        UserRegistrationForm user = new UserRegistrationForm();
        user.setUsername("username");
        user.setPassword("password");
        user.setConfirmPassword("password");
        user.setEmail("email@mail.com");
        return user;
    }

    public static ChannelCreationForm createChannelForm() {
        ChannelCreationForm form = new ChannelCreationForm();
        form.setTitle("channelTest");
        form.setCommunityTitle("communityTest");
        return form;
    }

    public static byte[] json(Object o) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(o);
    }

    public static User createUser() {
        User user = new User();
        user.setUsername("userTest");
        user.setEmail("test@test.com");
        user.setPassword("passwordTest");
        return user;
    }

    public static Channel createChannel(Community community) {
        Channel channel = new Channel();
        channel.setTitle("channelTest");
        channel.setCommunity(community);
        return channel;
    }

    public static Subscription createSubscription(Community community, User user) {
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setCommunity(community);
        return subscription;
    }

    public static Community createCommunity() {
        Community community = new Community();
        community.setTitle("communityTest");
        community.setVisible(true);
        List<Channel> channels = new ArrayList<>();
        Channel channel = createChannel(community);
        channels.add(channel);
        community.setChannels(channels);
        return community;
    }

    public static Message createMessage(Channel channel) {
        Message message = new Message();
        message.setText("Hello");
        message.setChannel(channel);
        return message;
    }

    public static List<Message> createMessageList(Message message) {
        List<Message> messages = new ArrayList<>();
        messages.add(message);
        return messages;
    }

    public static List<Subscription> createSubscriptionList(Subscription subscription) {
        List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(subscription);
        return subscriptions;
    }
}

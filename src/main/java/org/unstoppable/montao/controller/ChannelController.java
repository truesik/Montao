package org.unstoppable.montao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.unstoppable.montao.entity.*;
import org.unstoppable.montao.model.ChannelCreationForm;
import org.unstoppable.montao.service.*;
import org.unstoppable.montao.validator.ChannelValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ChannelController {
    private static final int QUANTITY = 20;

    private final CommunityService communityService;
    private final ChannelService channelService;
    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserService userService;
    private final SubscriptionService subscriptionService;

    @Autowired
    public ChannelController(CommunityService communityService,
                             ChannelService channelService,
                             MessageService messageService,
                             UserService userService,
                             SimpMessagingTemplate messagingTemplate,
                             SubscriptionService subscriptionService) {
        this.communityService = communityService;
        this.channelService = channelService;
        this.messageService = messageService;
        this.userService = userService;
        this.messagingTemplate = messagingTemplate;
        this.subscriptionService = subscriptionService;
    }

    /**
     * Creates new channel in current community.
     *
     * @param communityTitle Community where channel should be created.
     * @param channelForm    New channel form.
     * @param result         Errors.
     * @return "Success" or "Failure".
     */
    @RequestMapping(value = "{communityTitle}/channels/new",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String addChannel(@PathVariable("communityTitle") String communityTitle,
                             @Valid @RequestBody ChannelCreationForm channelForm,
                             BindingResult result) {
        new ChannelValidator(channelService).validate(channelForm, result);
        // TODO: 23.07.2016 Сделать проверку пользователя, чтобы только авторизованный мог создавать новые каналы
        // TODO: 23.07.2016 Канал может создать только член данного сообщества
        if (result.hasErrors()) {
            return "failure";
        } else {
            Community community = communityService.getByTitle(communityTitle);
            Channel channel = channelForm.createChannel(community);
            channelService.add(channel);
            // Broadcast new channel to listeners
            messagingTemplate.convertAndSend("/topic/" + communityTitle + "/newchannel", channel);
            return "success";
        }
    }

    /**
     * Returns false if channel title already exist and true if not.
     *
     * @param communityTitle Community title.
     * @param title          Channel title.
     * @return String "true" or "false".
     */
    @RequestMapping(value = "{communityTitle}/channels/check_title", method = RequestMethod.POST)
    @ResponseBody
    public String checkTitle(@PathVariable("communityTitle") String communityTitle, String title) {
        return channelService.checkTitle(title, communityTitle).toString();
    }

    @RequestMapping(value = "{communityTitle}/channels/{channelTitle}", method = RequestMethod.GET)
    public String channel(@PathVariable("communityTitle") String communityTitle,
                          @PathVariable("channelTitle") String channelTitle,
                          Model model,
                          Principal principal) {
        Community community = communityService.getByTitle(communityTitle);
        model.addAttribute("channelList", community.getChannels());
        // Fill subscribed user list
        List<Subscription> subscriptions = subscriptionService.getByCommunity(community);
        model.addAttribute("userList", subscriptions);
        if (principal != null) {
            User user = userService.getByUsername(principal.getName());
            Boolean isSubscribed = subscriptionService.checkSubscription(community, user);
            model.addAttribute("subscribed", isSubscribed);
        } else {
            model.addAttribute("subscribed", false);
        }
        return "community";
    }

    @RequestMapping(value = "{communityTitle}/channels/{channelTitle}/messages/new", method = RequestMethod.POST)
    @ResponseBody
    public String newMessage(@PathVariable("communityTitle") String communityTitle,
                             @PathVariable("channelTitle") String channelTitle,
                             Principal principal,
                             String newMessage) {
        Community community = communityService.getByTitle(communityTitle);
        Channel currentChannel = community.getChannels().stream()
                .filter(channel -> channel.getTitle().equals(channelTitle))
                .findFirst()
                .orElse(null);
        Message message = new Message();
        message.setMessage(newMessage);
        User user = userService.getByUsername(principal.getName());
        message.setUser(user);
        message.setChannel(currentChannel);
        message.setReceivedTime(LocalDateTime.now());
        // Adds message to database
        messageService.add(message);
        // Broadcast message to channel
        messagingTemplate.convertAndSend("/topic/" + communityTitle + "/" + channelTitle, message);
        return "success";
    }

    @RequestMapping(value = "{communityTitle}/channels/{channelTitle}/messages",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Message> getMessages(@PathVariable("communityTitle") String communityTitle,
                                     @PathVariable("channelTitle") String channelTitle,
                                     int startRowPosition) {
        Community community = communityService.getByTitle(communityTitle);
        Channel currentChannel = community.getChannels().stream()
                .filter(channel -> channel.getTitle().equals(channelTitle))
                .findFirst()
                .orElse(null);
        return messageService.getByChannelWithLimitation(currentChannel, startRowPosition, QUANTITY);
    }

    @RequestMapping(value = "{communityTitle}/get_last_channel",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Channel getLastOpenedChannel(@PathVariable("communityTitle") String communityTitle) {
        Community community = communityService.getByTitle(communityTitle);
        Channel channel = community.getChannels().get(0);
        return channel;
    }

}

package org.unstoppable.projectstack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.unstoppable.projectstack.entity.Channel;
import org.unstoppable.projectstack.entity.Community;
import org.unstoppable.projectstack.model.ChannelCreationForm;
import org.unstoppable.projectstack.service.*;
import org.unstoppable.projectstack.validator.ChannelValidator;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping(value = "/api/channel")
public class ChannelRestController {
    private static final int QUANTITY = 20;

    private final CommunityService communityService;
    private final ChannelService channelService;
    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserService userService;
    private final SubscriptionService subscriptionService;

    @Autowired
    public ChannelRestController(CommunityService communityService,
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

    @RequestMapping(value = "/add_new", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addChannel(@Valid @RequestBody ChannelCreationForm channelForm,
                             BindingResult result) {
        new ChannelValidator(channelService).validate(channelForm, result);
        if (result.hasErrors()) {
            return "false";
        } else {
            Channel channel = channelForm.createChannel();
            channelService.add(channel);
            messagingTemplate.convertAndSend("/topic/" + channel.getCommunity().getTitle() + "/newChannelNotification",
                    channel);
            return "success";
        }
    }

    /**
     * Returns false if channel title already exist and true if not.
     *
     * @param channelTitle   Channel title.
     * @param communityTitle Community title.
     * @return String "true" or "false".
     */
    @RequestMapping(value = "/check_title", method = RequestMethod.POST)
    public String checkTitle(@RequestParam(name = "channelTitle") String channelTitle,
                             @RequestParam(name = "communityTitle") String communityTitle) {
        return channelService.checkTitle(channelTitle, communityTitle).toString();
    }

    @RequestMapping(value = "/get_last_opened_channel",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Channel getLastOpenedChannel(@RequestParam(name = "communityTitle") String communityTitle,
                                        Principal principal) {
        Community community = communityService.getByTitle(communityTitle);
        Channel channel = community.getChannels().get(0);
        return channel;
    }
}

package org.unstoppable.projectstack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unstoppable.projectstack.service.*;

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
}

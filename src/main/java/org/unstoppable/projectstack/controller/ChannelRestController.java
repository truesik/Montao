package org.unstoppable.projectstack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.unstoppable.projectstack.entity.Channel;
import org.unstoppable.projectstack.entity.Community;
import org.unstoppable.projectstack.model.ChannelCreationForm;
import org.unstoppable.projectstack.service.*;
import org.unstoppable.projectstack.validator.ChannelValidator;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/api/channel")
public class ChannelRestController {
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

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addChannel(@Valid @RequestBody ChannelCreationForm channelForm,
                                           BindingResult result,
                                           UriComponentsBuilder uriComponentsBuilder) {
        new ChannelValidator(channelService).validate(channelForm, result);
        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            Community community = communityService.getByTitle(channelForm.getCommunityTitle());
            Channel channel = channelForm.createChannel(community);
            channelService.add(channel);
            messagingTemplate.convertAndSend("/topic/" + channel.getCommunity().getTitle() + "/newChannelNotification",
                    channel);
            HttpHeaders headers = new HttpHeaders();
            URI location = uriComponentsBuilder.path("/{communityTitle}/channels/{channelTitle}")
                    .buildAndExpand(channel.getCommunity().getTitle(), channel.getTitle()).toUri();
            headers.setLocation(location);
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
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
        if (principal == null) {
            // If principal is null, get default channel (general)
            Community community = communityService.getByTitle(communityTitle);
            return community.getChannels().get(0);
        } else {
            // Else get last opened channel
            // TODO: 30.09.2016 fix this branch
            Community community = communityService.getByTitle(communityTitle);
            Channel channel = community.getChannels().get(0);
            return channel;
        }
    }

    @RequestMapping(value = "/get_channels",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Channel>> getChannels(@RequestParam(name = "communityTitle") String communityTitle) {
        Community community = communityService.getByTitle(communityTitle);
        List<Channel> channels = community.getChannels();
        if (!channels.isEmpty()) {
            return new ResponseEntity<>(channels, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

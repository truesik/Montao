package org.unstoppable.montao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.unstoppable.montao.entity.Channel;
import org.unstoppable.montao.entity.Community;
import org.unstoppable.montao.entity.Message;
import org.unstoppable.montao.entity.User;
import org.unstoppable.montao.service.CommunityService;
import org.unstoppable.montao.service.MessageService;
import org.unstoppable.montao.service.UserService;

import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/message")
public class MessageRestController {
    private static final int QUANTITY = 20;

    private final CommunityService communityService;
    private final MessageService messageService;
    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public MessageRestController(CommunityService communityService, UserService userService, MessageService messageService, SimpMessagingTemplate messagingTemplate) {
        this.communityService = communityService;
        this.messagingTemplate = messagingTemplate;
        this.userService = userService;
        this.messageService = messageService;
    }

    @PostMapping(value = "/add")
    public ResponseEntity addMessage(@RequestParam(value = "text") String text,
                                     @RequestParam(value = "communityTitle") String communityTitle,
                                     @RequestParam(value = "channelTitle") String channelTitle,
                                     Principal principal,
                                     UriComponentsBuilder uriComponentsBuilder) {
        if (principal != null) {
            Community community = communityService.getByTitle(communityTitle);
            if (community != null) {
                Channel currentChannel = community.getChannels().stream()
                        .filter(channel -> channel.getTitle().equals(channelTitle))
                        .findFirst()
                        .orElse(null);
                if (currentChannel != null) {
                    User user = userService.getByUsername(principal.getName());
                    Message message = createMessage(text, user, currentChannel);
                    // Adds message to database
                    messageService.add(message);
                    // Broadcast message to channel
                    messagingTemplate.convertAndSend("/topic/" + communityTitle + "/" + channelTitle, message);
                    URI location = uriComponentsBuilder
                            .path("/community/{communityTitle}/channel/{channelTitle}/")
                            .query("messages={uuid}")
                            .buildAndExpand(
                                    community.getTitle(),
                                    currentChannel.getTitle(),
                                    message.getUuid())
                            .toUri();
                    return ResponseEntity.created(location).build();
                }
                return ResponseEntity.badRequest().body("Channel not found");
            }
            return ResponseEntity.badRequest().body("Community not found");
        }
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("Allows to authorized users only");
    }

    private Message createMessage(String text, User user, Channel channel) {
        Message message = new Message();
        message.setUuid(UUID.randomUUID().toString());
        message.setText(text);
        message.setUser(user);
        message.setChannel(channel);
        message.setReceivedTime(LocalDateTime.now());
        return message;
    }

    @PostMapping(value = "/get_messages", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getMessages(@RequestParam(value = "communityTitle") String communityTitle,
                                      @RequestParam(value = "channelTitle") String channelTitle,
                                      @RequestParam(value = "startRowPosition") int startRowPosition) {
        Community community = communityService.getByTitle(communityTitle);
        if (community != null) {
            Channel currentChannel = community.getChannels().stream()
                    .filter(channel -> channel.getTitle().equals(channelTitle))
                    .findFirst()
                    .orElse(null);
            if (currentChannel == null) {
                return ResponseEntity.badRequest().body("Channel not found");
            }
            return ResponseEntity.ok(messageService.getByChannelWithLimitation(currentChannel, startRowPosition, QUANTITY));
        }
        return ResponseEntity.badRequest().body("Community not found");
    }
}

package org.unstoppable.projectstack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.unstoppable.projectstack.entity.Channel;
import org.unstoppable.projectstack.entity.Community;
import org.unstoppable.projectstack.entity.Message;
import org.unstoppable.projectstack.entity.User;
import org.unstoppable.projectstack.service.CommunityService;
import org.unstoppable.projectstack.service.MessageService;
import org.unstoppable.projectstack.service.UserService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/api/message")
public class MessageRestController {
    private static final int QUANTITY = 20;

    private final CommunityService communityService;
    private final MessageService messageService;
    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public MessageRestController(CommunityService communityService, SimpMessagingTemplate messagingTemplate, UserService userService, MessageService messageService) {
        this.communityService = communityService;
        this.messagingTemplate = messagingTemplate;
        this.userService = userService;
        this.messageService = messageService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Void> addMessage(@RequestParam(value = "newMessage") String newMessage,
                                           @RequestParam(value = "communityTitle") String communityTitle,
                                           @RequestParam(value = "channelTitle") String channelTitle,
                                           Principal principal) {
        Community community = communityService.getByTitle(communityTitle);
        Channel currentChannel = community.getChannels().stream()
                .filter(channel -> channel.getTitle().equals(channelTitle))
                .findFirst()
                .orElse(null);
        User user = userService.getByUsername(principal.getName());
        Message message = createMessage(newMessage, user, currentChannel);
        // Adds message to database
        messageService.add(message);
        // Broadcast message to channel
        messagingTemplate.convertAndSend("/topic/" + communityTitle + "/" + channelTitle, message);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private Message createMessage(String text, User user, Channel channel) {
        Message message = new Message();
        message.setMessage(text);
        message.setUser(user);
        message.setChannel(channel);
        message.setReceivedTime(LocalDateTime.now());
        return message;
    }

    @RequestMapping(value = "/get_messages", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Message>> getMessages(@RequestParam(value = "communityTitle") String communityTitle,
                                                     @RequestParam(value = "channelTitle") String channelTitle,
                                                     @RequestParam(value = "startRowPosition") int startRowPosition) {
        Community community = communityService.getByTitle(communityTitle);
        Channel currentChannel = community.getChannels().stream()
                .filter(channel -> channel.getTitle().equals(channelTitle))
                .findFirst()
                .orElse(null);
        if (currentChannel == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(
                messageService.getByChannelWithLimitation(currentChannel, startRowPosition, QUANTITY),
                HttpStatus.OK);
    }
}

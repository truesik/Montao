package org.unstoppable.montao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.unstoppable.montao.entity.Channel;
import org.unstoppable.montao.entity.Community;
import org.unstoppable.montao.exception.ChannelFormException;
import org.unstoppable.montao.exception.CommunityNotFoundException;
import org.unstoppable.montao.model.ChannelCreationForm;
import org.unstoppable.montao.service.ChannelService;
import org.unstoppable.montao.service.CommunityService;
import org.unstoppable.montao.validator.ChannelValidator;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/channels")
public class ChannelRestController {
    private final CommunityService communityService;
    private final ChannelService channelService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ChannelRestController(CommunityService communityService,
                                 ChannelService channelService,
                                 SimpMessagingTemplate messagingTemplate) {
        this.communityService = communityService;
        this.channelService = channelService;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addChannel(@Valid @RequestBody ChannelCreationForm channelForm,
                                     BindingResult result,
                                     UriComponentsBuilder uriComponentsBuilder) {
        new ChannelValidator(channelService).validate(channelForm, result);
        if (result.hasErrors()) {
            throw new ChannelFormException("Form validation failed");
        }
        Community community = communityService.getByTitle(channelForm.getCommunityTitle());
        if (community == null) {
            throw new CommunityNotFoundException("Community not found");
        }
        Channel channel = channelForm.createChannel(community);
        channelService.add(channel);
        messagingTemplate
            .convertAndSend("/topic/" + channel.getCommunity().getTitle() + "/newChannelNotification",
                channel);
        URI location = uriComponentsBuilder
            .path("/community/{communityTitle}/channels/{channelTitle}")
            .buildAndExpand(channel.getCommunity().getTitle(), channel.getTitle())
            .toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping(value = "/get_channel", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getChannelByTitle(@RequestParam String communityTitle, @RequestParam String channelTitle) {
        Community community = communityService.getByTitle(communityTitle);
        if (community == null) {
            throw new CommunityNotFoundException("Community not found");
        }
        Channel channel = community.getChannels().stream()
            .filter(c -> c.getTitle().equals(channelTitle))
            .findFirst()
            .orElse(null);
        return ResponseEntity.ok(channel);
    }

    @GetMapping(value = "/{channelId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getChannelById(@PathVariable("channelId") String channelId) {
        //todo добавить реализацию
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteChannel() {
        //todo добавить реализацию
        return ResponseEntity.badRequest().build();
    }

    @PutMapping(value = "/{channelId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity updateChannelById(@PathVariable("channelId") String channelId) {
        //todo добавить реализацию
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/get_channels", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getChannels(@RequestParam(name = "communityTitle") String communityTitle) {
        Community community = communityService.getByTitle(communityTitle);
        if (community == null) {
            throw new CommunityNotFoundException("Community not found");
        }
        List<Channel> channels = community.getChannels();
        if (channels.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(channels);
    }

    /**
     * Returns false if channel title already exist and true if not.
     *
     * @param channelTitle   Channel title.
     * @param communityTitle Community title.
     * @return String "true" or "false".
     */
    @PostMapping(value = "/check_title")
    public String checkTitle(String channelTitle, String communityTitle) {
        return channelService.checkTitle(channelTitle, communityTitle).toString();
    }
}

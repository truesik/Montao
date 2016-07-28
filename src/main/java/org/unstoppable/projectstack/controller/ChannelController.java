package org.unstoppable.projectstack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.unstoppable.projectstack.entity.Channel;
import org.unstoppable.projectstack.entity.Community;
import org.unstoppable.projectstack.model.ChannelCreationForm;
import org.unstoppable.projectstack.service.ChannelService;
import org.unstoppable.projectstack.service.CommunityService;
import org.unstoppable.projectstack.validator.ChannelValidator;

import javax.validation.Valid;

@Controller
public class ChannelController {
    private final CommunityService communityService;
    private final ChannelService channelService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ChannelController(CommunityService communityService, ChannelService channelService, SimpMessagingTemplate messagingTemplate) {
        this.communityService = communityService;
        this.channelService = channelService;
        this.messagingTemplate = messagingTemplate;
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
        new ChannelValidator(channelService, communityTitle).validate(channelForm, result);
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
}

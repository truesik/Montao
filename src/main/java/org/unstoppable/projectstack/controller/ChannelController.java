package org.unstoppable.projectstack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.unstoppable.projectstack.entity.Channel;
import org.unstoppable.projectstack.entity.Community;
import org.unstoppable.projectstack.model.ChannelCreationForm;
import org.unstoppable.projectstack.service.ChannelService;
import org.unstoppable.projectstack.service.CommunityService;

import javax.validation.Valid;

@Controller
public class ChannelController {
    private final CommunityService communityService;
    private final ChannelService channelService;

    @Autowired
    public ChannelController(CommunityService communityService, ChannelService channelService) {
        this.communityService = communityService;
        this.channelService = channelService;
    }

    @RequestMapping(value = "/channels/new", method = RequestMethod.GET)
    public String addChannelPage(Model model) {
        model.addAttribute("channelForm", new ChannelCreationForm());
        return "newchannel";
    }

    @RequestMapping(value = "/channels/new", method = RequestMethod.POST)
    public String addChannel(@Valid @ModelAttribute ChannelCreationForm channelForm) {
        Community community = communityService.getByTitle(communityTitle);
        Channel channel = channelForm.createChannel(community);
        channelService.add(channel);
        return "newchannel";
    }
}

package org.unstoppable.montao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unstoppable.montao.dao.ChannelDAO;
import org.unstoppable.montao.entity.Channel;

import java.util.List;

@Service
public class ChannelService {
    @Autowired
    private ChannelDAO channelDAO;

    public void add(Channel channel) {
        channelDAO.add(channel);
    }

    public Boolean checkTitle(String channelTitle, String communityTitle) {
        List<Channel> channels = channelDAO.getByCommunityTitle(communityTitle);
        for (Channel channel : channels) {
            if (channel.getTitle().equals(channelTitle)) {
                return false;
            }
        }
        return true;
    }
}

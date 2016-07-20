package org.unstoppable.projectstack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unstoppable.projectstack.dao.ChannelDAO;
import org.unstoppable.projectstack.entity.Channel;

@Service
public class ChannelService {
    @Autowired
    private ChannelDAO channelDAO;

    public void add(Channel channel) {
        channelDAO.add(channel);
    }
}

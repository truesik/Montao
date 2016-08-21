package org.unstoppable.projectstack.dao;

import org.unstoppable.projectstack.entity.Channel;
import org.unstoppable.projectstack.entity.Message;

import java.util.List;

public interface MessageDAO {
    void add(Message message);

    void delete(Message message);

    void update(Message message);

    List<Message> getByChannel(Channel channel);

    List<Message> getByChannelWithLimitation(Channel channel, int startRowPosition, int maxResult);
}

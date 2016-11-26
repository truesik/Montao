package org.unstoppable.montao.dao;

import org.unstoppable.montao.entity.Channel;
import org.unstoppable.montao.entity.Message;

import java.util.List;

public interface MessageDAO {
    void add(Message message);

    void delete(Message message);

    void update(Message message);

    List<Message> getByChannel(Channel channel);

    List<Message> getByChannelWithLimitation(Channel channel, int startRowPosition, int maxResult);
}

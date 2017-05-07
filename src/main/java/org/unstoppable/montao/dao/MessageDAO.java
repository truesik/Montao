package org.unstoppable.montao.dao;

import org.unstoppable.montao.entity.Channel;
import org.unstoppable.montao.entity.Message;

import java.util.List;

public interface MessageDAO {
    List<Message> findAll(int page, int limit);

    List<Message> findAllByChannelId(long channelId, int page, int limit);

    List<Message> findAllByUsername(String username, int page, int limit);

    Message findByUuid(String uuid);

    void add(Message message);

    void delete(Message message);

    void update(Message message);

    long totalCound();

    long countByChannelId(long channelId);

    long countByUsername(String username);
}

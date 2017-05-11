package org.unstoppable.montao.dao;

import org.unstoppable.montao.entity.Channel;

import java.math.BigInteger;
import java.util.List;

public interface ChannelDAO {
    void add(Channel channel);

    void delete(Channel channel);

    void update(Channel channel);

    List <Channel> findAll(int page, int limit);

    Channel findByTitle (String title);

    Channel findById (Long id);

    long totalCount();
}

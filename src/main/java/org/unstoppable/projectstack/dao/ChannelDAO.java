package org.unstoppable.projectstack.dao;

import org.unstoppable.projectstack.entity.Channel;

import java.math.BigInteger;
import java.util.List;

public interface ChannelDAO {
    List<Channel> getAll();

    Channel getById(BigInteger id);

    Channel getByTitle(String title);

    void delete(Channel channel);

    void add(Channel channel);

    void update(Channel channel);

    List<Channel> getByCommunityTitle(String title);
}

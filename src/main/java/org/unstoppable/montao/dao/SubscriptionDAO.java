package org.unstoppable.montao.dao;

import org.unstoppable.montao.entity.Community;
import org.unstoppable.montao.entity.Subscription;
import org.unstoppable.montao.entity.User;
import org.unstoppable.montao.model.CommunitySubscription;

import java.util.List;

public interface SubscriptionDAO {
    void add(Subscription subscription);

    void delete(Subscription subscription);

    List<Community> findByUser(User user, int page, int limit);

    List<User> findByCommunity(Community community, int page, int limit);

    long communitiesCountByUser(User user);

    long userCountByCommunity(Community community);
}

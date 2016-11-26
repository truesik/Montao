package org.unstoppable.montao.dao;

import org.unstoppable.montao.entity.Community;
import org.unstoppable.montao.entity.Subscription;
import org.unstoppable.montao.entity.User;
import org.unstoppable.montao.model.CommunitySubscription;

import java.util.List;

public interface SubscriptionDAO {
    void add(Subscription subscription);

    Subscription getSubscription(Community community, User user);

    void delete(Subscription subscription);

    List<Subscription> getByUser(User user);

    List<Subscription> getByCommunity(Community community);

    List<CommunitySubscription> getCommunitiesWithSubscriptionsByUser(User user, int startRowPosition, int maxResult);
}

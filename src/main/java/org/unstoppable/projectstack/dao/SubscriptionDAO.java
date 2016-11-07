package org.unstoppable.projectstack.dao;

import org.unstoppable.projectstack.entity.Community;
import org.unstoppable.projectstack.entity.Subscription;
import org.unstoppable.projectstack.entity.User;
import org.unstoppable.projectstack.model.CommunitySubscription;

import java.util.List;

public interface SubscriptionDAO {
    Subscription add(Subscription subscription);

    Subscription getSubscription(Community community, User user);

    Subscription delete(Subscription subscription);

    List<Subscription> getByUser(User user);

    List<Subscription> getByCommunity(Community community);

    List<CommunitySubscription> getCommunitiesWithSubscriptionsByUser(User user, int startRowPosition, int maxResult);
}

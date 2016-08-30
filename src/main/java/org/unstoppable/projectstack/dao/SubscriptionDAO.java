package org.unstoppable.projectstack.dao;

import org.unstoppable.projectstack.entity.Community;
import org.unstoppable.projectstack.entity.Subscription;
import org.unstoppable.projectstack.entity.User;

public interface SubscriptionDAO {
    void add(Subscription subscription);

    Subscription getSubscription(Community community, User user);

    void delete(Subscription subscription);
}

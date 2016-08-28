package org.unstoppable.projectstack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unstoppable.projectstack.dao.SubscriptionDAO;
import org.unstoppable.projectstack.entity.Community;
import org.unstoppable.projectstack.entity.Subscription;
import org.unstoppable.projectstack.entity.User;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionDAO subscriptionDAO;

    public void subscribe(Subscription newSubscription) {
        subscriptionDAO.add(newSubscription);
    }

    public Boolean checkSubscription(Community community, User user) {
        Subscription subscription = subscriptionDAO.getSubscription(community, user);
        return subscription != null;
    }
}

package org.unstoppable.projectstack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unstoppable.projectstack.dao.SubscriptionDAO;
import org.unstoppable.projectstack.entity.Community;
import org.unstoppable.projectstack.entity.Subscription;
import org.unstoppable.projectstack.entity.User;
import org.unstoppable.projectstack.model.CommunitySubscription;

import java.util.List;

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

    public Subscription get(Community community, User user) {
        return subscriptionDAO.getSubscription(community, user);
    }

    public void delete(Subscription subscription) {
        subscriptionDAO.delete(subscription);
    }

    public List<Subscription> getByUser(User user) {
        return subscriptionDAO.getByUser(user);
    }

    public List<CommunitySubscription> getCommunitiesWithSubscriptionsByUser(User user,
                                                                             int startRowPosition,
                                                                             int maxResult) {
        return subscriptionDAO.getCommunitiesWithSubscriptionsByUser(user, startRowPosition, maxResult);
    }
}

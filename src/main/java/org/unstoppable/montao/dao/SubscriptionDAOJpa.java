package org.unstoppable.montao.dao;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unstoppable.montao.entity.Community;
import org.unstoppable.montao.entity.Subscription;
import org.unstoppable.montao.entity.User;
import org.unstoppable.montao.model.CommunitySubscription;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class SubscriptionDAOJpa implements SubscriptionDAO{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(Subscription subscription) {entityManager.persist(subscription);}

    @Override
    public Subscription getSubscription(Community community, User user) {

    }

    @Override
    public void delete(Subscription subscription) {entityManager.remove(subscription);}

    @Override
    public List<Subscription> getByUser(User user) {
        String jpql = "FROM Subscription where subscription.user = :user";
        return entityManager.createQuery(jpql,Subscription.class).getResultList();
    }

    @Override
    public List<Subscription> getByCommunity(Community community) {
        return null;
    }

    @Override
    public List<CommunitySubscription> getCommunitiesWithSubscriptionsByUser(User user, int startRowPosition, int maxResult) {
        return null;
    }
}

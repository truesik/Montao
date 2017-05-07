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
    public void delete(Subscription subscription) {entityManager.remove(subscription);}

    @Override
    public List<Community> findByUser(User user, int page, int limit) {
        return entityManager
            .createQuery("SELECT s FROM Subscription s WHERE s.user =:user", Community.class)
            .setParameter("user",user)
            .setFirstResult((page-1)*limit)
            .setMaxResults(limit)
            .getResultList();
    }

    @Override
    public List<User> findByCommunity(Community community, int page, int limit) {
        return entityManager
            .createQuery("SELECT s FROM Subscription s WHERE s.community =:community", User.class)
            .setParameter("community",community)
            .setFirstResult((page-1)*limit)
            .setMaxResults(limit)
            .getResultList();
    }

    @Override
    public long communitiesCountByUser(User user) {
        return (long) entityManager
            .createQuery("SELECT COUNT (s.id) FROM  Subscription s WHERE s.user =:user")
            .setParameter("user", user)
            .getSingleResult();
    }

    @Override
    public long userCountByCommunity(Community community) {
        return (long) entityManager
            .createQuery("SELECT COUNT (s.id) FROM  Subscription s WHERE s.community =:community")
            .setParameter("community", community)
            .getSingleResult();
    }


}

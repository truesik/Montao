package org.unstoppable.projectstack.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unstoppable.projectstack.entity.Community;
import org.unstoppable.projectstack.entity.Subscription;
import org.unstoppable.projectstack.entity.User;
import org.unstoppable.projectstack.model.CommunitySubscription;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class SubscriptionDAOHibernate implements SubscriptionDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(Subscription subscription) {
        sessionFactory.getCurrentSession().save(subscription);
    }

    @Override
    public Subscription getSubscription(Community community, User user) {
        String hql = "FROM Subscription WHERE community = :community AND user = :user";
        Query<Subscription> query = sessionFactory.getCurrentSession().createQuery(hql, Subscription.class);
        query.setParameter("community", community);
        query.setParameter("user", user);
        return query.uniqueResult();
    }

    @Override
    public void delete(Subscription subscription) {
        sessionFactory.getCurrentSession().delete(subscription);
    }

    @Override
    public List<Subscription> getByUser(User user) {
        String hql = "FROM Subscription WHERE user = :user";
        Query<Subscription> query = sessionFactory.getCurrentSession().createQuery(hql, Subscription.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

    @Override
    public List<Subscription> getByCommunity(Community community) {
        String hql = "FROM Subscription WHERE community = :community";
        Query<Subscription> query = sessionFactory.getCurrentSession().createQuery(hql, Subscription.class);
        query.setParameter("community", community);
        return query.getResultList();
    }

    @Override
    public List<CommunitySubscription> getCommunitiesWithSubscriptionsByUser(User user,
                                                                             int startRowPosition,
                                                                             int maxResult) {
        List<CommunitySubscription> communitySubscriptions = new ArrayList<>();
        String sql = "" +
                "SELECT c.id            AS id, " +
                "       c.title         AS title, " +
                "       c.description   AS description, " +
                "       CASE " +
                "           WHEN s.community_id IS NULL " +
                "           THEN false " +
                "           ELSE true " +
                "       END             AS isSubscribed " +
                "FROM communities AS c " +
                "   LEFT JOIN ( SELECT * " +
                "               FROM subscriptions " +
                "               WHERE user_id = :userId) AS s ON c.id = s.community_id";
        NativeQuery nativeQuery = sessionFactory.getCurrentSession().createNativeQuery(sql);
        nativeQuery.setParameter("userId", user.getId());
        nativeQuery.setFirstResult(startRowPosition);
        nativeQuery.setMaxResults(maxResult);
        List<Object[]> list = nativeQuery.list();
        for (Object[] row : list) {
            CommunitySubscription communitySubscription = new CommunitySubscription();
            communitySubscription.setId(((BigDecimal) row[0]).toBigInteger());
            communitySubscription.setTitle(row[1].toString());
            communitySubscription.setDescription(row[2] == null ? "" : row[2].toString());
            communitySubscription.setSubscribed((Boolean) row[3]);
            communitySubscriptions.add(communitySubscription);
        }
        return communitySubscriptions;
    }
}

package org.unstoppable.projectstack.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unstoppable.projectstack.entity.Community;
import org.unstoppable.projectstack.entity.Subscription;
import org.unstoppable.projectstack.entity.User;

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
}

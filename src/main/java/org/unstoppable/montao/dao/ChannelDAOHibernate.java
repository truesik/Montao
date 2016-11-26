package org.unstoppable.montao.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unstoppable.montao.entity.Channel;

import java.math.BigInteger;
import java.util.List;

@Repository
@Transactional
public class ChannelDAOHibernate implements ChannelDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Channel> getAll() {
        String hql = "FROM Channel";
        return sessionFactory.getCurrentSession().createQuery(hql, Channel.class).list();
    }

    @Override
    public Channel getById(BigInteger id) {
        return sessionFactory.getCurrentSession().find(Channel.class, id);
    }

    @Override
    public Channel getByTitle(String title) {
        String hql = "FROM Channel WHERE title = :title";
        Query<Channel> query = sessionFactory.getCurrentSession().createQuery(hql, Channel.class);
        query.setParameter("title", title);
        return query.uniqueResult();
    }

    @Override
    public void delete(Channel channel) {
        sessionFactory.getCurrentSession().delete(channel);
    }

    @Override
    public void add(Channel channel) {
        sessionFactory.getCurrentSession().save(channel);
    }

    @Override
    public void update(Channel channel) {
        sessionFactory.getCurrentSession().update(channel);
    }

    @Override
    public List<Channel> getByCommunityTitle(String title) {
        String hql = "FROM Channel WHERE community.title = :title ";
        Query<Channel> query = sessionFactory.getCurrentSession().createQuery(hql, Channel.class);
        query.setParameter("title", title);
        return query.getResultList();
    }
}

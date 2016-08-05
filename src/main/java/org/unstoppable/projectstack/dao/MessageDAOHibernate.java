package org.unstoppable.projectstack.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unstoppable.projectstack.entity.Channel;
import org.unstoppable.projectstack.entity.Message;

import java.util.List;

@Repository
@Transactional
public class MessageDAOHibernate implements MessageDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(Message message) {
        sessionFactory.getCurrentSession().save(message);
    }

    @Override
    public void delete(Message message) {
        sessionFactory.getCurrentSession().delete(message);
    }

    @Override
    public void update(Message message) {
        sessionFactory.getCurrentSession().update(message);
    }

    @Override
    public List<Message> getByChannel(Channel channel) {
        String hql = "FROM Message WHERE channel = :channel";
        Query<Message> query = sessionFactory.getCurrentSession().createQuery(hql, Message.class);
        query.setParameter("channel", channel);
        return query.getResultList();
    }
}

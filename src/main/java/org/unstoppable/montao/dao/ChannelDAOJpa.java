package org.unstoppable.montao.dao;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unstoppable.montao.entity.Channel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

@Repository
@Transactional
public class ChannelDOAJpa implements ChannelDAO{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Channel> getAll() {
        String jpql = "FROM Channel";
        return entityManager.createQuery(jpql, Channel.class).getResultList();
    }

    @Override
    public Channel getById(BigInteger id) {
        return entityManager.find(Channel.class, id);
    }

    @Override
    public Channel getByTitle(String title) {
        return entityManager.find(Channel.class, title);
    }

    @Override
    public void delete(Channel channel) { entityManager.remove(channel);    }

    @Override
    public void add(Channel channel) { entityManager.persist(channel); }

    @Override
    public void update(Channel channel) { entityManager.merge(channel); }

    @Override
    public List<Channel> getByCommunityTitle(String title) {
        String jpql = "SELECT COUNT(title) FROM Channel";
        Query query = entityManager.createQuery(jpql, Channel.class);
        return  query.getSingleResult();
    }
}

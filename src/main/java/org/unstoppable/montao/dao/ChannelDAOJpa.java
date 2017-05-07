package org.unstoppable.montao.dao;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unstoppable.montao.entity.Channel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
@Transactional
public class ChannelDAOJpa implements ChannelDAO{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(Channel channel) {
        entityManager.persist(channel);
    }

    @Override
    public void delete(Channel channel) {
        entityManager.remove(channel);
    }

    @Override
    public void update(Channel channel) {
        entityManager.merge(channel);
    }

    @Override
    public List<Channel> findAll(int page, int limit) {
        return entityManager
            .createQuery("SELECT c FROM Channel c",Channel.class)
            .setMaxResults(limit)
            .setFirstResult((page - 1)* limit)
            .getResultList();
    }

    @Override
    public Channel findByTitle(String title) {
        return entityManager
            .createQuery("SELECT c FROM Channel c WHERE c.title=:title",Channel.class)
            .setParameter("title", title)
            .getResultList()
            .stream()
            .findFirst()
            .orElseThrow(()->{ throw new NoSuchElementException("Channel with $title title not found");});
    }

    @Override
    public Channel findById(Long id) {
        return entityManager
            .createQuery("SELECT c FROM Channel c WHERE c.id=:id",Channel.class)
            .setParameter("id", id)
            .getResultList()
            .stream()
            .findFirst()
            .orElseThrow(()->{ throw new NoSuchElementException("Channel with $id id not found");});
    }

    @Override
    public long totalCount() {
        return (long) entityManager
            .createQuery("SELECT COUNT(c.id) FROM Channel c")
            .getSingleResult();
    }
}

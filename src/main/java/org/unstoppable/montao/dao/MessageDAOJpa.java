package org.unstoppable.montao.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unstoppable.montao.entity.Channel;
import org.unstoppable.montao.entity.Community;
import org.unstoppable.montao.entity.Message;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
@Transactional
public class MessageDAOJpa implements MessageDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Message> findAll(int page, int limit) {
        return entityManager
            .createQuery("SELECT m FROM Message m", Message.class)
            .setFirstResult((page - 1) * limit)
            .setMaxResults(limit)
            .getResultList();
    }

    @Override
    public List<Message> findAllByChannelId(long channelId, int page, int limit) {
        return entityManager
            .createQuery("SELECT m FROM Message m WHERE m.channel.id=:channelId", Message.class)
            .setParameter("channelId", channelId)
            .setFirstResult((page - 1) * limit)
            .setMaxResults(limit)
            .getResultList();
    }

    @Override
    public List<Message> findAllByUsername(String username, int page, int limit) {
        return entityManager
            .createQuery("SELECT m FROM Message m WHERE m.user.name=:username")
            .setParameter("username", username)
            .setFirstResult((page - 1) * limit)
            .setMaxResults(limit)
            .getResultList();
    }

    @Override
    public Message findByUuid(String uuid) {
        return entityManager
            .createQuery("SELECT m FROM Message m WHERE m.uuid=:uuid", Message.class)
            .setParameter("uuid", uuid)
            .getResultList()
            .stream()
            .findFirst()
            .orElseThrow(()->{ throw new NoSuchElementException("Message with $uuid uuid not found");});
    }

    @Override
    public void add(Message message) {entityManager.persist(message);}

    @Override
    public void delete(Message message) {entityManager.remove(message);}

    @Override
    public void update(Message message) {entityManager.merge(message);}

    @Override
    public long totalCound() {
        return (long) entityManager
            .createQuery("SELECT COUNT(m.id) FROM Message m")
            .getSingleResult();
    }

    @Override
    public long countByChannelId(long channelId) {
        return (long) entityManager
            .createQuery("SELECT m FROM Message m WHERE m.channel.id =:channelId")
            .setParameter("channelId",channelId)
            .getSingleResult();
    }

    @Override
    public long countByUsername(String username) {
        return (long) entityManager
            .createQuery("SELECT m FROM Message m WHERE m.user.username =:username")
            .setParameter("username",username)
            .getSingleResult();
    }
}

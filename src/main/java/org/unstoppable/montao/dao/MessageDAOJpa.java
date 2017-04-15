package org.unstoppable.montao.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unstoppable.montao.entity.Channel;
import org.unstoppable.montao.entity.Message;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class MessageDAOJpa implements MessageDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(Message message) {entityManager.persist(message);}

    @Override
    public void delete(Message message) {entityManager.remove(message);}

    @Override
    public void update(Message message) {entityManager.merge(message);}

    @Override
    public List<Message> getByChannel(Channel channel) {
        String jpql = "FROM Message where channel= :channel";
        return entityManager.createQuery(jpql,Message.class).getResultList();
    }

    @Override
    public List<Message> getByChannelWithLimitation(Channel channel, int startRowPosition, int maxResult) {
        return null;
    }
}

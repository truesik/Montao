package org.unstoppable.montao.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unstoppable.montao.entity.Channel;
import org.unstoppable.montao.entity.Community;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.List;

@Repository
@Transactional
public class CommunityDAOJpa implements CommunityDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Community> getAll() {
        String jpql = "FROM Community";
        return entityManager.createQuery(jpql, Community.class).getResultList();
    }

    @Override
    public Community getById(BigInteger id) {
        return entityManager.find(Community.class, id);
    }

    @Override
    public Community getByTitle(String title) {
        return entityManager.find(Community.class, title);
    }

    @Override
    public void delete(Community community) {entityManager.remove(community);}

    @Override
    public void add(Community community) {entityManager.persist(community);}

    @Override
    public void update(Community community) {entityManager.merge(community);}

    @Override
    public List<Community> getLimitedPublicCollection(int startRowPosition, int maxResult) {
        String jpql = "FROM Community";
        return entityManager.createQuery(jpql,Community.class).getResultList();
    }
}

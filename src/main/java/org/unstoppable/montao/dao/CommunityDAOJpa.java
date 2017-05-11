package org.unstoppable.montao.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unstoppable.montao.entity.Channel;
import org.unstoppable.montao.entity.Community;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
@Transactional
public class CommunityDAOJpa implements CommunityDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(Community community) {entityManager.persist(community);}

    @Override
    public void delete(Community community) {entityManager.remove(community);}

    @Override
    public void update(Community community) {entityManager.merge(community);}

    @Override
    public List<Community> findAll(int limit, int page) {
        String jpql = "SELECT c FROM Community c";
        return entityManager
            .createQuery(jpql, Community.class)
            .setMaxResults(limit)
            .setFirstResult((page - 1) * limit)
            .getResultList();
    }

    @Override
    public Community findByTitle(String title) {
        return entityManager
            .createQuery("SELECT c FROM Community c WHERE c.title=:title", Community.class)
            .setParameter("title", title)
            .getResultList()
            .stream()
            .findFirst()
            .orElseThrow(()->{ throw new NoSuchElementException("Community with $title title not found");});
    }

    @Override
    public long totalCount() {
        return (long) entityManager
            .createQuery("SELECT COUNT(c.id) FROM Community c")
            .getSingleResult();
    }
}

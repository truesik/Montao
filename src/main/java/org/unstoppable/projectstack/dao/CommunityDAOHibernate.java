package org.unstoppable.projectstack.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unstoppable.projectstack.entity.Community;

import java.math.BigInteger;
import java.util.List;

@Repository
@Transactional
public class CommunityDAOHibernate implements CommunityDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Community> getAll() {
        String hql = "FROM User";
        return sessionFactory.getCurrentSession().createQuery(hql, Community.class).list();
    }

    @Override
    public Community getById(BigInteger id) {
        return sessionFactory.getCurrentSession().find(Community.class, id);
    }

    @Override
    public Community getByTitle(String title) {
        String hql = "FROM Community WHERE title = :title";
        Query<Community> query = sessionFactory.getCurrentSession().createQuery(hql, Community.class);
        query.setParameter("title", title);
        return query.uniqueResult();
    }

    @Override
    public void delete(Community community) {
        sessionFactory.getCurrentSession().delete(community);
    }

    @Override
    public void add(Community community) {
        sessionFactory.getCurrentSession().save(community);
    }

    @Override
    public void update(Community community) {
        sessionFactory.getCurrentSession().update(community);
    }
}

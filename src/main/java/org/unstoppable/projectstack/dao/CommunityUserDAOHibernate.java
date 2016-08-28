package org.unstoppable.projectstack.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unstoppable.projectstack.entity.Community;
import org.unstoppable.projectstack.entity.CommunityUser;
import org.unstoppable.projectstack.entity.User;

@Repository
@Transactional
public class CommunityUserDAOHibernate implements CommunityUserDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(CommunityUser communityUser) {
        sessionFactory.getCurrentSession().save(communityUser);
    }

    @Override
    public CommunityUser getUser(Community community, User user) {
        String hql = "FROM CommunityUser WHERE community = :community AND user = :user";
        Query<CommunityUser> query = sessionFactory.getCurrentSession().createQuery(hql, CommunityUser.class);
        query.setParameter("community", community);
        query.setParameter("user", user);
        return query.uniqueResult();
    }
}

package org.unstoppable.projectstack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.unstoppable.projectstack.dao.CommunityDAO;
import org.unstoppable.projectstack.entity.Community;

public class CommunityService {
    @Autowired
    private CommunityDAO communityDAO;

    public void save(Community community) {
        communityDAO.add(community);
    }

    public Boolean checkTitle(String title) {
        Community community = communityDAO.getByTitle(title);
        return community == null;
    }

}

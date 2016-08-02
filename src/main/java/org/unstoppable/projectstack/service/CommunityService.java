package org.unstoppable.projectstack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.unstoppable.projectstack.dao.CommunityDAO;
import org.unstoppable.projectstack.dao.UserDAO;
import org.unstoppable.projectstack.entity.Community;
import org.unstoppable.projectstack.entity.User;

import java.util.List;

public class CommunityService {
    @Autowired
    private CommunityDAO communityDAO;
    @Autowired
    private UserDAO userDAO;

    public void save(Community community) {
        communityDAO.add(community);
    }

    public Boolean checkTitle(String title) {
        // check community title existence
        Community community = communityDAO.getByTitle(title);
        // check same username existence
        User user = userDAO.getByUsername(title);
        return community == null && user == null;
    }

    public Community getByTitle(String communityTitle) {
        return communityDAO.getByTitle(communityTitle);
    }

    public List<Community> getPublicCommunities(int startRowPosition, int maxResult) {
        return communityDAO.getLimitedPublicCollection(startRowPosition, maxResult);
    }
}

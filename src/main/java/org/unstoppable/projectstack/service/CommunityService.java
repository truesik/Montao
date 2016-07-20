package org.unstoppable.projectstack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.unstoppable.projectstack.dao.CommunityDAO;
import org.unstoppable.projectstack.entity.Channel;
import org.unstoppable.projectstack.entity.Community;

import java.util.List;

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

    public List<Channel> getChannels(String communityTitle) {
        Community community = communityDAO.getByTitle(communityTitle);
        return community.getChannels();
    }

    public Community getByTitle(String communityTitle) {
        return communityDAO.getByTitle(communityTitle);
    }
}

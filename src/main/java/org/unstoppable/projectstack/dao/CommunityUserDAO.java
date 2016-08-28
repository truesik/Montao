package org.unstoppable.projectstack.dao;

import org.unstoppable.projectstack.entity.Community;
import org.unstoppable.projectstack.entity.CommunityUser;
import org.unstoppable.projectstack.entity.User;

public interface CommunityUserDAO {
    void add(CommunityUser communityUser);
    CommunityUser getUser(Community community, User user);
}

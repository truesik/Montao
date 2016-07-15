package org.unstoppable.projectstack.dao;

import org.unstoppable.projectstack.entity.Community;

import java.math.BigInteger;
import java.util.List;

public interface CommunityDAO {
    List<Community> getAll();

    Community getById(BigInteger id);

    Community getByTitle(String title);

    void delete(Community community);

    void add(Community community);

    void update(Community community);
}

package org.unstoppable.montao.dao;

import org.unstoppable.montao.entity.Community;

import java.math.BigInteger;
import java.util.List;

public interface CommunityDAO {
    void add(Community community);

    void delete(Community community);

    void update(Community community);

    List<Community> findAll(int page, int limit);

    Community findByTitle(String title);

    long totalCount ();
}

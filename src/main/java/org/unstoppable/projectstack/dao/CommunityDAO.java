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

    /**
     * Returns only public communities with ability to set limitations of result list.
     *
     * @param startRowPosition Start row position.
     * @param maxResult Result list limitation.
     * @return Community list.
     */
    List<Community> getLimitedPublicCollection(int startRowPosition, int maxResult);
}

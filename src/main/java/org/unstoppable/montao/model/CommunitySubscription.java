package org.unstoppable.montao.model;

import java.math.BigInteger;

public class CommunitySubscription {
    private BigInteger id;
    private String title;
    private String description;
    private Boolean isSubscribed;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        isSubscribed = subscribed;
    }

    @Override
    public String toString() {
        return "CommunitySubscription{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isSubscribed=" + isSubscribed +
                '}';
    }
}

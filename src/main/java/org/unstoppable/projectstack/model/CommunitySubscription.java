package org.unstoppable.projectstack.model;

public class CommunitySubscription {
    private String title;
    private String description;
    private Boolean isSubscribed;

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

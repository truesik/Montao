package org.unstoppable.projectstack.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.unstoppable.projectstack.entity.Channel;
import org.unstoppable.projectstack.entity.Community;

import javax.validation.constraints.Size;

public class ChannelCreationForm {
    private static final int MIN_TITLE_LENGTH = 4;

    @NotEmpty
    @Size(min = MIN_TITLE_LENGTH)
    private String title;
    private String description;
    @NotEmpty
    private String community;

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

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public Channel createChannel(Community community) {
        Channel channel = new Channel();
        channel.setTitle(title);
        channel.setDescription(description);
        channel.setCommunity(community);
        return channel;
    }
}

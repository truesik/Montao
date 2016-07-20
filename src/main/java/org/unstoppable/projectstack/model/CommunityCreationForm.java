package org.unstoppable.projectstack.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.unstoppable.projectstack.entity.Community;
import org.unstoppable.projectstack.entity.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Used to validate new community information before creation.
 */
public class CommunityCreationForm {
    private static final int MIN_TITLE_LENGTH = 4;

    @NotEmpty
    @Size(min = MIN_TITLE_LENGTH)
    private String title;
    private String description;

    @NotNull
    private User founder;

    @NotNull
    private Boolean isVisible;

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

    public User getFounder() {
        return founder;
    }

    public void setFounder(User founder) {
        this.founder = founder;
    }

    public Boolean getVisible() {
        return isVisible;
    }

    public void setVisible(Boolean visible) {
        isVisible = visible;
    }

    /**
     * Creates community based on information from the form.
     *
     * @return Community.
     */
    public Community createCommunity() {
        Community community = new Community();
        community.setTitle(title);
        community.setDescription(description);
        community.setVisible(isVisible);
        community.setFounder(founder);
        community.setCreationDate(LocalDate.now());
        return community;
    }
}

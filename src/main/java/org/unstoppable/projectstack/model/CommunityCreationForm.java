package org.unstoppable.projectstack.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.unstoppable.projectstack.entity.Community;
import org.unstoppable.projectstack.entity.User;

import java.time.LocalDate;

/**
 * Used to validate new community information before creation.
 */
public class CommunityCreationForm {
    @NotEmpty
    private String title;
    private String description;
    @NotEmpty
    private User founder;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommunityCreationForm that = (CommunityCreationForm) o;

        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (founder != null ? !founder.equals(that.founder) : that.founder != null) return false;
        return isVisible != null ? isVisible.equals(that.isVisible) : that.isVisible == null;

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (founder != null ? founder.hashCode() : 0);
        result = 31 * result + (isVisible != null ? isVisible.hashCode() : 0);
        return result;
    }
}

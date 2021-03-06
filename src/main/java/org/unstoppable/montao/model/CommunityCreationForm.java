package org.unstoppable.montao.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.unstoppable.montao.entity.Community;
import org.unstoppable.montao.entity.User;

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
    private String founder;

    @NotNull
    private boolean isVisible;

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

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public boolean getVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    /**
     * Creates community based on information from the form.
     *
     * @return Community.
     */
    public Community createCommunity(User founder) {
        Community community = new Community();
        community.setTitle(title);
        community.setDescription(description);
        community.setVisible(isVisible);
        community.setFounder(founder);
        community.setCreationDate(LocalDate.now());
        return community;
    }
}

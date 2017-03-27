package org.unstoppable.montao.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

/**
 * Table that contains list of communities.
 */
@Entity
@Indexed
@Table(name = "communities")
public class Community {
    private static final int MIN_TITLE_LENGTH = 4;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @Column(name = "title", nullable = false, unique = true)
    @NotEmpty
    @Field
    @Size(min = MIN_TITLE_LENGTH)
    private String title;

    @Field
    @Column(name = "description")
    private String description;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "founder_id")
    private User founder;

    @Column(name = "is_visible", nullable = false)
    @NotNull
    private Boolean isVisible;

    @JsonManagedReference
    @OneToMany(mappedBy = "community", fetch = FetchType.EAGER)
    private List<Channel> channels;

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

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public User getFounder() {
        return founder;
    }

    public void setFounder(User founder) {
        this.founder = founder;
    }

    public Boolean isVisible() {
        return isVisible;
    }

    public void setVisible(Boolean visible) {
        isVisible = visible;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Community community = (Community) o;

        if (id != null ? !id.equals(community.id) : community.id != null) return false;
        if (title != null ? !title.equals(community.title) : community.title != null) return false;
        if (description != null ? !description.equals(community.description) : community.description != null)
            return false;
        if (creationDate != null ? !creationDate.equals(community.creationDate) : community.creationDate != null)
            return false;
        if (founder != null ? !founder.equals(community.founder) : community.founder != null) return false;
        return isVisible != null ? isVisible.equals(community.isVisible) : community.isVisible == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (founder != null ? founder.hashCode() : 0);
        result = 31 * result + (isVisible != null ? isVisible.hashCode() : 0);
        return result;
    }
}

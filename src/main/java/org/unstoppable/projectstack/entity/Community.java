package org.unstoppable.projectstack.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * Table that contains list of communities.
 */
@Entity
@Table(name = "communities")
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @Column(name = "title", nullable = false, unique = true)
    @NotEmpty
    private String title;

    @Column(name = "description")
    private String description;

    @Temporal(TemporalType.DATE)
    @Column(name = "creation_date", nullable = false)
    @NotEmpty
    private String creationDate;

    @ManyToOne
    @JoinColumn(name = "founder_id")
    private User founder;

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

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public User getFounder() {
        return founder;
    }

    public void setFounder(User founder) {
        this.founder = founder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Community community = (Community) o;

        if (!id.equals(community.id)) return false;
        if (!title.equals(community.title)) return false;
        if (description != null ? !description.equals(community.description) : community.description != null)
            return false;
        if (!creationDate.equals(community.creationDate)) return false;
        return founder.equals(community.founder);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + creationDate.hashCode();
        result = 31 * result + founder.hashCode();
        return result;
    }
}

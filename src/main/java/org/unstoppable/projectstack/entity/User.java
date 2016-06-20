package org.unstoppable.projectstack.entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Table that contains users.
 */
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "USERNAME", nullable = false)
    @NotNull
    @NotEmpty
    @Size(min = 3)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    @NotNull
    @NotEmpty
    private String password;

    @Column(name = "EMAIL", nullable = false)
    @Email
    @NotNull
    @NotEmpty
    private String email;

    @Column(name = "IS_CONFIRMED", nullable = false)
    @NotNull
    @NotEmpty
    private Boolean isConfirmed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(Boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }
}

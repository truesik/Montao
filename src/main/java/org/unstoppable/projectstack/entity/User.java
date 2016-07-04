package org.unstoppable.projectstack.entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.time.LocalDate;

/**
 * Table that contains users.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @Column(name = "username", nullable = false, unique = true)
    @NotEmpty
    @Size(min = 3)
    private String username;

    @Column(name = "password", nullable = false)
    @NotEmpty
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotEmpty
    private String email;

    @Column(name = "role", nullable = false)
    @NotEmpty
    private String role;

    @Column(name = "is_confirmed", nullable = false)
    @NotNull
    private Boolean isConfirmed = false;

    @Column(name = "is_locked", nullable = false)
    @NotNull
    private Boolean isLocked = false;

    @Column(name = "registration_date", nullable = false)
    @NotNull
    private LocalDate registrationDate;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.isConfirmed = confirmed;
    }

    public Boolean isLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        if (!username.equals(user.username)) return false;
        if (!password.equals(user.password)) return false;
        if (!email.equals(user.email)) return false;
        if (!role.equals(user.role)) return false;
        if (!isConfirmed.equals(user.isConfirmed)) return false;
        if (!isLocked.equals(user.isLocked)) return false;
        return registrationDate.equals(user.registrationDate);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + role.hashCode();
        result = 31 * result + isConfirmed.hashCode();
        result = 31 * result + isLocked.hashCode();
        result = 31 * result + registrationDate.hashCode();
        return result;
    }
}

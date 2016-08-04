package org.unstoppable.projectstack.entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Table that contains users.
 */
@Entity
@Table(name = "users")
public class User {
    private static final int MIN_USERNAME_LENGTH = 4;
    private static final int MIN_PASSWORD_LENGTH = 8;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @NotNull
    private String uuid;

    @Column(name = "username", nullable = false, unique = true)
    @NotEmpty
    @Size(min = MIN_USERNAME_LENGTH)
    private String username;

    @Column(name = "password", nullable = false)
    @NotEmpty
    @Size(min = MIN_PASSWORD_LENGTH)
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
    private Boolean isConfirmed;

    @Column(name = "is_locked", nullable = false)
    @NotNull
    private Boolean isLocked;

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (uuid != null ? !uuid.equals(user.uuid) : user.uuid != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (role != null ? !role.equals(user.role) : user.role != null) return false;
        if (isConfirmed != null ? !isConfirmed.equals(user.isConfirmed) : user.isConfirmed != null) return false;
        if (isLocked != null ? !isLocked.equals(user.isLocked) : user.isLocked != null) return false;
        return registrationDate != null ? registrationDate.equals(user.registrationDate) : user.registrationDate == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (isConfirmed != null ? isConfirmed.hashCode() : 0);
        result = 31 * result + (isLocked != null ? isLocked.hashCode() : 0);
        result = 31 * result + (registrationDate != null ? registrationDate.hashCode() : 0);
        return result;
    }
}

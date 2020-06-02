package com.jukusoft.mmo.data.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jukusoft.mmo.data.entity.AbstractEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users", indexes = {
        //@Index(columnList = "email", name = "email_idx"),
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = "username", name = "username_uqn")
})
@Cacheable//use second level cache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserEntity extends AbstractEntity {

    @Size(min = 2, max = 45)
    @Column(name = "username", unique = true, nullable = false, updatable = true)
    @NotEmpty(message = "username is required")
    private String username;

    /**
     * if password is null, no login is allowed
     */
    @Size(min = 2, max = 255)
    @Column(name = "password", unique = false, nullable = true, updatable = true)
    //@NotEmpty(message = "password is required")
    private String password;

    @Size(min = 2, max = 255)
    @Column(name = "salt", unique = false, nullable = true, updatable = true)
    private String salt;

    @JsonIgnore
    @OneToOne(mappedBy = "user", orphanRemoval = true, optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserPreferencesEntity userPreferences;

    @ManyToMany(/*mappedBy = "id", */cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<RoleEntity> roles = new ArrayList<>();

    @Size(min = 2, max = 255)
    @Column(name = "last_ip_address", unique = false, nullable = true, updatable = true)
    private String lastIP;

    public UserEntity(@Size(min = 2, max = 45) @NotEmpty(message = "username is required") String username) {
        this.username = username;
    }

    private UserEntity() {
        //
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public UserPreferencesEntity getUserPreferences() {
        if (userPreferences == null) {
            userPreferences = new UserPreferencesEntity(this);
        }

        return userPreferences;
    }

    public List<RoleEntity> listRoles() {
        return roles;
    }

    public void addRole(RoleEntity role) {
        if (!this.roles.contains(role)) {
            this.roles.add(role);
        }
    }

    public void removeRole(RoleEntity role) {
        this.roles.remove(role);
    }

    @PrePersist
    public final void prePersist1() {
        if (this.userPreferences == null) {
            this.userPreferences = new UserPreferencesEntity(this);
        }
    }

}

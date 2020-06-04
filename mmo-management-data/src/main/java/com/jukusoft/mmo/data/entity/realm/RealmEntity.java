package com.jukusoft.mmo.data.entity.realm;

import com.jukusoft.mmo.data.entity.AbstractEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "realms", indexes = {
        //@Index(columnList = "email", name = "email_idx"),
}, uniqueConstraints = {
        //@UniqueConstraint(columnNames = "username", name = "username_uqn")
})
@Cacheable//use second level cache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RealmEntity extends AbstractEntity {

    @Size(min = 2, max = 45)
    @Column(name = "name", unique = true, nullable = false, updatable = true)
    @NotEmpty(message = "name is required")
    private String name;

    @Size(min = 2, max = 45)
    @Column(name = "title", unique = false, nullable = false, updatable = true)
    @NotEmpty(message = "title is required")
    private String title;

    @Embedded
    private StartPosition startPosition;

    @Size(min = 2, max = 45)
    @Column(name = "requiredPermission", unique = false, nullable = false, updatable = true)
    @NotEmpty(message = "requiredPermission is required")
    private String requiredPermission;

    public RealmEntity(@Size(min = 2, max = 45) @NotEmpty(message = "name is required") String name, @Size(min = 2, max = 45) @NotEmpty(message = "title is required") String title) {
        this.name = name;
        this.title = title;
        this.startPosition = new StartPosition();
        this.requiredPermission = "none";
    }

    protected RealmEntity() {
        //
    }

    public StartPosition getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(StartPosition startPosition) {
        this.startPosition = startPosition;
    }

    public String getRequiredPermission() {
        return requiredPermission;
    }

    public void setRequiredPermission(String requiredPermission) {
        this.requiredPermission = requiredPermission;
    }

}

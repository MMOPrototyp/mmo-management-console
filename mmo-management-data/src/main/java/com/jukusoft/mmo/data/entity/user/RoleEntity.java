package com.jukusoft.mmo.data.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jukusoft.mmo.data.entity.AbstractEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "roles", indexes = {
        //@Index(columnList = "email", name = "email_idx"),
}, uniqueConstraints = {
        //@UniqueConstraint(columnNames = "username", name = "username_uqn")
})
@Cacheable//use second level cache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RoleEntity extends AbstractEntity {

    /**
     * unique name (this is not the title, which is shown in frontend!)
     */
    @Size(min = 2, max = 45)
    @Column(name = "name", nullable = false, updatable = true, unique = true)
    private String name;

    @Size(min = 2, max = 45)
    @Column(name = "title", nullable = false, updatable = true)
    private String title;

    @ManyToMany(/*mappedBy = "id", */cascade = {}, fetch = FetchType.LAZY)
    private List<UserEntity> members = new ArrayList<>();

    @ElementCollection(/* targetClass=String.class,  */fetch = FetchType.LAZY)
    @JoinTable(name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"))
    @MapKeyColumn(name = "token")
    @Column(name = "value")
    private Set<PermissionEntity> permissions;

    /**
     * constructor for global role
     *
     * @param name  unique role name
     * @param title role title
     */
    public RoleEntity(@Size(min = 2, max = 45) String name, @Size(min = 2, max = 45) String title) {
        this.name = name;
        this.title = title;
        this.permissions = new HashSet<>();
    }

    public RoleEntity(@Size(min = 2, max = 45) String name) {
        this.name = name;
        this.title = UUID.randomUUID().toString();
        this.permissions = new HashSet<>();
    }

    private RoleEntity() {
        //
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public Set<PermissionEntity> listPermissions() {
        return permissions;
    }

    public void addPermission(PermissionEntity permissionEntity) {
        Objects.requireNonNull(permissionEntity);
        this.permissions.add(permissionEntity);
    }

    public void removePermission(PermissionEntity permissionEntity) {
        this.permissions.remove(permissionEntity);
    }

}

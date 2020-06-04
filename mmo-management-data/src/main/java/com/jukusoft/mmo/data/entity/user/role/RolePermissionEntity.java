package com.jukusoft.mmo.data.entity.user.role;

import com.jukusoft.mmo.data.entity.user.PermissionEntity;
import com.jukusoft.mmo.data.entity.user.RoleEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "role_permissions", indexes = {
        //@Index(columnList = "email", name = "email_idx"),
}, uniqueConstraints = {
        //@UniqueConstraint(columnNames = "username", name = "username_uqn")
})
@Cacheable//use second level cache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@IdClass(RolePermissionID.class)
public class RolePermissionEntity {

    @Id
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false, updatable = false)
    private RoleEntity roleID;

    @Id
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "permission_token", nullable = false, updatable = false)
    private PermissionEntity permissionID;

    @Column(name = "begins_at", nullable = true, updatable = true)
    private Date beginsAt;

    @Column(name = "ends_at", nullable = true, updatable = true)
    private Date endsAt;

    public RolePermissionEntity(RoleEntity role, PermissionEntity permission) {
        this.roleID = role;
        this.permissionID = permission;
    }

    public RolePermissionEntity() {
        //
    }

    public RoleEntity getRole() {
        return roleID;
    }

    public PermissionEntity getPermission() {
        return permissionID;
    }

    public Date getBeginsAt() {
        return beginsAt;
    }

    public Date getEndsAt() {
        return endsAt;
    }

}

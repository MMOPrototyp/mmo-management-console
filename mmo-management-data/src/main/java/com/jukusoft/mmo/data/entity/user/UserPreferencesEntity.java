package com.jukusoft.mmo.data.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jukusoft.mmo.data.entity.AbstractEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "user_preferences", indexes = {
        //@Index(columnList = "customer", name = "customer_idx"),
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id"}, name = "user_uqn")
})
@Cacheable//use second level cache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserPreferencesEntity extends AbstractEntity {

    @JsonIgnore
    @OneToOne(orphanRemoval = false, optional = false, cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
//don't use an extra table, use join column instead
    private UserEntity user;

    //TODO: add code here

    /**
     * default constructor is required for spring
     */
    protected UserPreferencesEntity() {
        //
    }

    /**
     * private constructor, so noone can create an instance of it - instance will be created automatically, if UserEntity object is added
     */
    protected UserPreferencesEntity(UserEntity user) {
        this.user = user;
    }

}

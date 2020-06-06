package com.jukusoft.mmo.data.entity.map;

import com.jukusoft.mmo.data.entity.AbstractEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * a zone contains one or many regions.
 */
@Entity
@Table(name = "game_zones", indexes = {
        //@Index(columnList = "email", name = "email_idx"),
}, uniqueConstraints = {
        //@UniqueConstraint(columnNames = "username", name = "username_uqn")
})
@Cacheable//use second level cache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ZoneEntity extends AbstractEntity {

    @Size(min = 2, max = 45)
    @Column(name = "name", unique = true, nullable = false, updatable = false)
    @NotEmpty(message = "name is required")
    private String name;

    @OneToMany(mappedBy = "zone", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Column(name = "zone")
    private List<RegionEntity> regions;

    public ZoneEntity(@Size(min = 2, max = 45) @NotEmpty(message = "name is required") String name) {
        this.name = name;
        this.regions = new ArrayList<>();
    }

    protected ZoneEntity() {
        //
    }

    public String getName() {
        return name;
    }

    public void addRegion(RegionEntity region) {
        Objects.requireNonNull(region);
        this.regions.add(region);
    }

    public void removeRegion(RegionEntity region) {
        Objects.requireNonNull(region);
        this.regions.remove(region);
    }

}

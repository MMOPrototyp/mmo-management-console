package com.jukusoft.mmo.data.entity.map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jukusoft.mmo.data.entity.AbstractEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "game_maps", indexes = {
        //@Index(columnList = "email", name = "email_idx"),
}, uniqueConstraints = {
        //@UniqueConstraint(columnNames = "username", name = "username_uqn")
})
@Cacheable//use second level cache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MapEntity extends AbstractEntity {

    @ManyToOne(optional = false, cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false, updatable = false)//don't use an extra table, use join column instead
    @JsonIgnore
    private RegionEntity region;

    @Size(min = 2, max = 45)
    @Column(name = "name", unique = true, nullable = false, updatable = true)
    @NotEmpty(message = "name is required")
    private String name;

    @Size(min = 2, max = 90)
    @Column(name = "path", unique = true, nullable = false, updatable = true)
    @NotEmpty(message = "path is required")
    private String path;

    public MapEntity(RegionEntity region, @Size(min = 2, max = 45) @NotEmpty(message = "name is required") String name, @Size(min = 2, max = 90) @NotEmpty(message = "path is required") String path) {
        this.region = region;
        this.name = name;
        this.path = path;
    }

    protected MapEntity() {
        //
    }

    public RegionEntity getRegion() {
        return region;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

}

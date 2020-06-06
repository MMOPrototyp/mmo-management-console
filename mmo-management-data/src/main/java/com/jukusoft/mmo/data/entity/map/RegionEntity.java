package com.jukusoft.mmo.data.entity.map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jukusoft.mmo.data.entity.AbstractEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "game_regions", indexes = {
        //@Index(columnList = "email", name = "email_idx"),
}, uniqueConstraints = {
        //@UniqueConstraint(columnNames = "username", name = "username_uqn")
})
@Cacheable//use second level cache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RegionEntity extends AbstractEntity {

    @ManyToOne(optional = false, cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id", nullable = false, updatable = false)//don't use an extra table, use join column instead
    @JsonIgnore
    private ZoneEntity zone;

    @Size(min = 2, max = 45)
    @Column(name = "name", unique = true, nullable = false, updatable = true)
    @NotEmpty(message = "name is required")
    private String name;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Column(name = "region")
    private List<MapEntity> maps;

    public RegionEntity(ZoneEntity zone, @Size(min = 2, max = 45) @NotEmpty(message = "name is required") String name) {
        this.zone = zone;
        this.name = name;
        this.maps = new ArrayList<>();
    }

    protected RegionEntity() {
        //
    }

    public ZoneEntity getZone() {
        return zone;
    }

    public String getName() {
        return name;
    }

    public void addMap(MapEntity map) {
        Objects.requireNonNull(map);
        maps.add(map);
    }

    public void removeMap(MapEntity map) {
        Objects.requireNonNull(map);
        maps.remove(map);
    }

}

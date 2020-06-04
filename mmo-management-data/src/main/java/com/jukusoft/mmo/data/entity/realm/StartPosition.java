package com.jukusoft.mmo.data.entity.realm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jukusoft.mmo.data.entity.map.MapEntity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Embeddable
public class StartPosition {

    @ManyToOne(optional = false, cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "start_map_id", nullable = false, updatable = false)//don't use an extra table, use join column instead
    @JsonIgnore
    private MapEntity startMap;

    /**
     * x pos of start position in map
     */
    @Column(name = "x_pos")
    private float posX;

    /**
     * y pos of start position in map
     */
    @Column(name = "y_pos")
    private float posY;

    public StartPosition(MapEntity startMap, float posX, float posY) {
        this.startMap = startMap;
        this.posX = posX;
        this.posY = posY;
    }

    protected StartPosition() {
        //
    }

    public MapEntity getStartMap() {
        return startMap;
    }

    public void setStartMap(MapEntity startMap) {
        this.startMap = startMap;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

}

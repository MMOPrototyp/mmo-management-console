package com.jukusoft.mmo.service.statistics;

public class StatisticsDTO {

    protected long userCount;
    protected long zoneCount;
    protected long regionCount;
    protected long mapCount;

    public StatisticsDTO(long userCount, long zoneCount, long regionCount, long mapCount) {
        this.userCount = userCount;
        this.zoneCount = zoneCount;
        this.regionCount = regionCount;
        this.mapCount = mapCount;
    }

    public long getUserCount() {
        return userCount;
    }

    public long getZoneCount() {
        return zoneCount;
    }

    public long getRegionCount() {
        return regionCount;
    }

    public long getMapCount() {
        return mapCount;
    }
    
}

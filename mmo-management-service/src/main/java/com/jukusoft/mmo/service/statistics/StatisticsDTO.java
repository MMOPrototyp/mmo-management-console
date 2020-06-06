package com.jukusoft.mmo.service.statistics;

public class StatisticsDTO {

    protected long userCount;
    protected long zoneCount;
    protected long regionCount;
    protected long mapCount;

    protected long proxyServerOnlineCount;
    protected long minProxyServerOnlineCountAlertLevel;
    protected long gameServerOnlineCount;
    protected long minGameServerOnlineCountAlertLevel;

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

    public long getProxyServerOnlineCount() {
        return proxyServerOnlineCount;
    }

    public long getMinProxyServerOnlineCountAlertLevel() {
        return minProxyServerOnlineCountAlertLevel;
    }

    public long getGameServerOnlineCount() {
        return gameServerOnlineCount;
    }

    public long getMinGameServerOnlineCountAlertLevel() {
        return minGameServerOnlineCountAlertLevel;
    }

}

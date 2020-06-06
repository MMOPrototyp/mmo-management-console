package com.jukusoft.mmo.service.statistics;

import com.jukusoft.mmo.data.dao.MapDAO;
import com.jukusoft.mmo.data.dao.RegionDAO;
import com.jukusoft.mmo.data.dao.UserDAO;
import com.jukusoft.mmo.data.dao.ZoneDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ZoneDAO zoneDAO;

    @Autowired
    private RegionDAO regionDAO;

    @Autowired
    private MapDAO mapDAO;

    public StatisticsDTO getStatistics() {
        return new StatisticsDTO(userDAO.count(), zoneDAO.count(), regionDAO.count(), mapDAO.count());
    }

}

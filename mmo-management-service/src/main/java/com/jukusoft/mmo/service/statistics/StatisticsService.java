package com.jukusoft.mmo.service.statistics;

import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    public StatisticsDTO getStatistics() {
        return new StatisticsDTO();
    }

}

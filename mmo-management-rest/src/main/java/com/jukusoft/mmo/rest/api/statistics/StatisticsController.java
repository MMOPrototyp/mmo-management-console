package com.jukusoft.mmo.rest.api.statistics;

import com.jukusoft.mmo.service.statistics.StatisticsDTO;
import com.jukusoft.mmo.service.statistics.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @PreAuthorize("hasAuthority('global.can.see.statistics')")
    @GetMapping("/api/statistics")
    public ResponseEntity<StatisticsDTO> listStatistics() {
        return ResponseEntity.ok().cacheControl(CacheControl.noCache()).body(statisticsService.getStatistics());
    }

}

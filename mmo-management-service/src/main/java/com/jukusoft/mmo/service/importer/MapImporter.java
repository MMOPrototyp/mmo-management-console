package com.jukusoft.mmo.service.importer;

import com.jukusoft.mmo.core.utils.ImportUtils;
import com.jukusoft.mmo.service.setting.MapImporterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

/**
 * this importer imports all zones, regions and maps
 */
@Configuration
@Profile("default")
@DependsOn({"permission_import", "user_import"})
public class MapImporter implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(MapImporter.class);

    @Autowired
    private MapImporterService mapImporterService;

    @Bean(name = "map_import")
    @Override
    public void afterPropertiesSet() throws Exception {
        if (!ImportUtils.isInitialImportEnabled()) {
            return;
        }

        logger.info("import maps now");
    }

}

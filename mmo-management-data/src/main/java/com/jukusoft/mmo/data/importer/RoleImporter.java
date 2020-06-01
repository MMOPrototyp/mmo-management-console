package com.jukusoft.mmo.data.importer;

import com.jukusoft.mmo.core.utils.ImportUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("default")
@DependsOn({"permission_import", "user_import"})
public class RoleImporter implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(RoleImporter.class);

    @Autowired
    private RoleImporterService roleImporterService;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!ImportUtils.isInitialImportEnabled()) {
            return;
        }

        roleImporterService.importRoles();
    }

}

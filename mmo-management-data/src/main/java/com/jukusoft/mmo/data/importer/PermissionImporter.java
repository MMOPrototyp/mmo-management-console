package com.jukusoft.mmo.data.importer;

import com.google.common.io.Resources;
import com.jukusoft.mmo.core.utils.ImportUtils;
import com.jukusoft.mmo.data.dao.PermissionDAO;
import com.jukusoft.mmo.data.entity.user.PermissionEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.net.URL;
import java.nio.charset.StandardCharsets;

@Configuration
@Profile("default")
public class PermissionImporter implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(PermissionImporter.class);

    @Autowired
    private PermissionDAO permissionDAO;

    @Bean(name = "permission_import")
    @Override
    public void afterPropertiesSet() throws Exception {
        if (!ImportUtils.isInitialImportEnabled()) {
            return;
        }

        logger.info("create or update permissions");

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource("permissions/permissions.json");
        String content = Resources.toString(url, StandardCharsets.UTF_8);

        JSONArray jsonArray = new JSONArray(content);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);

            String token = json.getString("token");
            String title = json.getString("title");
            PermissionEntity.PermissionType type = PermissionEntity.PermissionType.valueOf(json.getString("type").toUpperCase());

            //save or override permission
            PermissionEntity permission = new PermissionEntity(token, title, type);
            permissionDAO.save(permission);
        }

        logger.info("{} permissions found in database", permissionDAO.count());
    }

}

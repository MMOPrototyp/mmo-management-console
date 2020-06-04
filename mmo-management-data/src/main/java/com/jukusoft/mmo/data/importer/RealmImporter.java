package com.jukusoft.mmo.data.importer;

import com.jukusoft.mmo.core.utils.ImportUtils;
import com.jukusoft.mmo.core.utils.ResourceUtils;
import com.jukusoft.mmo.data.dao.RealmDAO;
import com.jukusoft.mmo.data.entity.realm.RealmEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

import java.util.Objects;

@Configuration
@Profile("default")
public class RealmImporter implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(RealmImporter.class);

    @Autowired
    private RealmDAO realmDAO;

    @Bean(name = "realm_import")
    @Override
    public void afterPropertiesSet() throws Exception {
        if (!ImportUtils.isInitialImportEnabled()) {
            return;
        }

        logger.info("import realms");

        String jsonString = ResourceUtils.getResourceFileAsString("realms/realms.json");
        Objects.requireNonNull(jsonString);

        JSONArray jsonArray = new JSONArray(jsonString);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);

            String name = json.getString("name");
            String title = json.getString("title");

            //check, if realm already exists
            if (realmDAO.existsByName(name)) {
                logger.info("skip realm '{}', because realm already exists", name);
                continue;
            }

            logger.info("create new realm: {}", name);

            RealmEntity realm = new RealmEntity(name, title);
            realmDAO.save(realm);
        }
    }

}

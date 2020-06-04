package com.jukusoft.mmo.data.importer;

import com.jukusoft.mmo.core.utils.ImportUtils;
import com.jukusoft.mmo.core.utils.ResourceUtils;
import com.jukusoft.mmo.data.dao.MapDAO;
import com.jukusoft.mmo.data.dao.RealmDAO;
import com.jukusoft.mmo.data.entity.map.MapEntity;
import com.jukusoft.mmo.data.entity.realm.RealmEntity;
import com.jukusoft.mmo.data.entity.realm.StartPosition;
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

import javax.persistence.Embedded;
import java.util.Objects;
import java.util.Optional;

@Configuration
@Profile("default")
public class RealmImporter implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(RealmImporter.class);

    @Autowired
    private RealmDAO realmDAO;

    @Autowired
    private MapDAO mapDAO;

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

            JSONObject startPosJSON = json.getJSONObject("startPosition");
            String startMap = startPosJSON.getString("mapUniqueName");
            float posX = startPosJSON.getFloat("x");
            float posY = startPosJSON.getFloat("y");

            RealmEntity realm = null;

            //check, if realm already exists
            if (realmDAO.existsByName(name)) {
                logger.info("skip realm '{}', because realm already exists", name);
                realm = realmDAO.findByName(name).orElseThrow(() -> new RuntimeException("realm not found with name '" + name + "'"));
            } else {
                logger.info("create new realm: {}", name);
                realm = new RealmEntity(name, title);
            }

            //find map
            Optional<MapEntity> mapEntityOptional = mapDAO.findByName(startMap);

            if (!mapEntityOptional.isPresent()) {
                logger.error("start map from realm is not present: {}", startMap);
                continue;
            }

            //set start position
            realm.getStartPosition().setStartMap(mapEntityOptional.get());
            realm.getStartPosition().setPosX(posX);
            realm.getStartPosition().setPosY(posY);

            realmDAO.save(realm);
        }
    }

}

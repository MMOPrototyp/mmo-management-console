package com.jukusoft.mmo.service.setting;

import com.jukusoft.mmo.core.utils.FileUtils;
import com.jukusoft.mmo.data.dao.ZoneDAO;
import com.jukusoft.mmo.data.entity.map.ZoneEntity;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Optional;

@Service
public class MapImporterService {

    private static final Logger logger = LoggerFactory.getLogger(MapImporterService.class);

    @Autowired
    private ZoneDAO zoneDAO;

    @Transactional
    public void importDir(File dir) throws IOException {
        Objects.requireNonNull(dir);

        if (!dir.exists()) {
            throw new IllegalStateException("zone directory does not exists: " + dir.getAbsolutePath());
        }

        Files.list(dir.toPath())
                .map(path -> path.toFile())
                .filter(file -> file.isDirectory())
                .filter(file -> !file.getName().contains("."))
                .forEach(zoneDir -> importZone(zoneDir));
    }

    public void importZone(File zoneDir) {
        Objects.requireNonNull(zoneDir);

        if (!zoneDir.exists()) {
            throw new IllegalStateException("zone directory does not exists: " + zoneDir.getAbsolutePath());
        }

        if (!zoneDir.isDirectory()) {
            throw new IllegalStateException("path is not a directory: " + zoneDir.getAbsolutePath());
        }

        File jsonFile = new File(zoneDir, "zone.json");
        String content = "";

        try {
            content = FileUtils.readFile(jsonFile.getAbsolutePath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.warn("Cannot read zone.json: " + jsonFile.getAbsolutePath(), e);
            return;
        }

        JSONObject json = new JSONObject(content);
        String name = json.getString("name");

        ZoneEntity zone = createAndGetZone(name);

        //TODO: add code here
    }

    private ZoneEntity createAndGetZone(String uniqueName) {
        Optional<ZoneEntity> zoneEntityOptional = zoneDAO.findByName(uniqueName);
        ZoneEntity zone = null;

        if (!zoneEntityOptional.isPresent()) {
            //create a new zone
            logger.info("create new zone '{}'", uniqueName);

            zone = new ZoneEntity(uniqueName);
            zone = zoneDAO.save(zone);
        } else {
            //zone already exists
            zone = zoneEntityOptional.get();
        }

        return zone;
    }

}

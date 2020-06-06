package com.jukusoft.mmo.service.setting;

import com.jukusoft.mmo.core.utils.FileUtils;
import com.jukusoft.mmo.data.dao.RegionDAO;
import com.jukusoft.mmo.data.dao.ZoneDAO;
import com.jukusoft.mmo.data.entity.map.RegionEntity;
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
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
public class MapImporterService {

    private static final Logger logger = LoggerFactory.getLogger(MapImporterService.class);

    @Autowired
    private ZoneDAO zoneDAO;

    @Autowired
    private RegionDAO regionDAO;

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

        //import regions
        try {
            listDir(zoneDir, dir -> importRegion(zone, dir));
        } catch (IOException e) {
            logger.warn("IOException while list regions in zone: " + zoneDir.getAbsolutePath(), e);
        }
    }

    private void listDir(File dir, Consumer<File> consumer) throws IOException {
        Files.list(dir.toPath())
                .map(path -> path.toFile())
                .filter(file -> file.isDirectory())
                .filter(file -> !file.getName().contains("."))
                .forEach(consumer::accept);
    }

    public void importRegion(ZoneEntity zone, File regionDir) {
        Objects.requireNonNull(regionDir);

        if (!regionDir.exists()) {
            throw new IllegalStateException("region directory does not exists: " + regionDir.getAbsolutePath());
        }

        if (!regionDir.isDirectory()) {
            throw new IllegalStateException("path is not a directory: " + regionDir.getAbsolutePath());
        }

        File jsonFile = new File(regionDir, "region.json");
        String content = "";

        try {
            content = FileUtils.readFile(jsonFile.getAbsolutePath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.warn("Cannot read region.json: " + jsonFile.getAbsolutePath(), e);
            return;
        }

        JSONObject json = new JSONObject(content);
        String name = json.getString("name");

        RegionEntity region = createAndGetRegion(zone, name);

        //TODO: import maps in region
    }

    @Transactional
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

    @Transactional
    private RegionEntity createAndGetRegion(ZoneEntity zone, String uniqueName) {
        Optional<RegionEntity> regionEntityOptional = regionDAO.findByName(uniqueName);
        RegionEntity region = null;

        if (!regionEntityOptional.isPresent()) {
            //create a new zone
            logger.info("create new region '{}'", uniqueName);

            region = new RegionEntity(zone, uniqueName);
            region = regionDAO.save(region);
        } else {
            //zone already exists
            region = regionEntityOptional.get();
        }

        return region;
    }

}

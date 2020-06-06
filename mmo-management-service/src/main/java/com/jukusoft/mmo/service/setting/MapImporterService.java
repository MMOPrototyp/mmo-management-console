package com.jukusoft.mmo.service.setting;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

@Service
public class MapImporterService {

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

        //TODO: add code here
    }

}

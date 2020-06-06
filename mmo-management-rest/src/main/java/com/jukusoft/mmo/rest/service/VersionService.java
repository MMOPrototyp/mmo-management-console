package com.jukusoft.mmo.rest.service;

import com.jukusoft.mmo.rest.api.version.VersionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Service;

@Service
public class VersionService {

    private final BuildProperties buildProperties;

    //we only need to parse the information once, then we can used the cached information
    private VersionDTO cachedVersionDTO = null;

    @Autowired
    public VersionService(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    public VersionDTO getBackendVersion() {
        //use cached version, if available
        if (this.cachedVersionDTO != null) {
            return this.cachedVersionDTO;
        }

        String versionStr = this.buildProperties.getVersion();
        String buildNumber = this.buildProperties.get("build.number");
        String buildTimestamp = this.buildProperties.get("build.time");
        String gitBuildHash = this.buildProperties.get("git.build.hash");

        //String buildTime = this.buildProperties.getTime().toString();

        VersionDTO version = new VersionDTO(versionStr, buildNumber, buildTimestamp, gitBuildHash);
        version.setType("backend");

        this.cachedVersionDTO = version;
        return this.cachedVersionDTO;
    }

}

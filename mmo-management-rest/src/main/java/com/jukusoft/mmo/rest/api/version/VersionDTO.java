package com.jukusoft.mmo.rest.api.version;

public class VersionDTO {

    private String type = "backend";

    private String version;
    private String buildNumber;
    private String buildTime;
    private String gitBuildHash;

    public VersionDTO(String version, String buildNumber, String buildTime, String gitBuildHash) {
        this.version = version;
        this.buildNumber = buildNumber;
        this.buildTime = buildTime;
        this.gitBuildHash = gitBuildHash;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public String getBuildTime() {
        return buildTime;
    }

    public String getGitBuildHash() {
        return gitBuildHash;
    }

}

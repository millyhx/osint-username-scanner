package com.milly.osint.core;

import java.util.Map;

public class ScanResult {

    private final String siteName;
    private final boolean exists;
    private final String profileUrl;
    private final Map<String, String> metadata;

    public ScanResult(String siteName, boolean exists, String profileUrl, Map<String, String> metadata) {
        this.siteName = siteName;
        this.exists = exists;
        this.profileUrl = profileUrl;
        this.metadata = metadata;
    }

    public String getSiteName() {
        return siteName;
    }

    public boolean exists() {
        return exists;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }


}

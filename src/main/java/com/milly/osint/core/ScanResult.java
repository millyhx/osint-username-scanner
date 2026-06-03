package com.milly.osint.core;

public class ScanResult {

    private final String siteName;
    private final boolean exists;
    private final String profileUrl;

    public ScanResult(String siteName, boolean exists, String profileUrl) {
        this.siteName = siteName;
        this.exists = exists;
        this.profileUrl = profileUrl;
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
}

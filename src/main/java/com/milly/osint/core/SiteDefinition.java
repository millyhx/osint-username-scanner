package com.milly.osint.core;

public class SiteDefinition {

    private String name;
    private String url;
    private int existsWhenStatus;
    private MetadataExtractor metadataExtractor;


    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getExistsWhenStatus() {
        return existsWhenStatus;
    }

    public MetadataExtractor getMetadataExtractor() {
        return metadataExtractor;
    }

    public void setMetadataExtractor(MetadataExtractor metadataExtractor) {
        this.metadataExtractor = metadataExtractor;
    }


}

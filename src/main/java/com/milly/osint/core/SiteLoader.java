package com.milly.osint.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.List;

public class SiteLoader {

    public static List<SiteDefinition> loadSites() {
        try {
            InputStream in = SiteLoader.class.getResourceAsStream("/sites.json");
            ObjectMapper mapper = new ObjectMapper();
            return List.of(mapper.readValue(in, SiteDefinition[].class));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load sites.json", e);
        }
    }
}

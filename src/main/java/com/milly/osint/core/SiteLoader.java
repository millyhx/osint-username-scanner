package com.milly.osint.core;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class SiteLoader {

    public static List<SiteDefinition> loadSites() {
        try {
            InputStream in = SiteLoader.class.getResourceAsStream("/sites.json");
            ObjectMapper mapper = new ObjectMapper();

            SiteDefinition[] sites = mapper.readValue(in, SiteDefinition[].class);

            return Arrays.asList(sites);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load sites.json", e);
        }
    }
}

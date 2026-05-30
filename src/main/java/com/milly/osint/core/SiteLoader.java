package com.milly.osint.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.milly.osint.core.metadata.GitHubMetadataExtractor;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class SiteLoader {

    public static List<SiteDefinition> loadSites() {
        try {
            InputStream in = SiteLoader.class.getResourceAsStream("/sites.json");
            ObjectMapper mapper = new ObjectMapper();

            // Load all sites from JSON
            SiteDefinition[] sites = mapper.readValue(in, SiteDefinition[].class);

            // Attach metadata extractors
            for (SiteDefinition site : sites) {
                if (site.getName().equalsIgnoreCase("GitHub")) {
                    site.setMetadataExtractor(new GitHubMetadataExtractor());
                }

                // Add more extractors here later:
                // if (site.getName().equalsIgnoreCase("Instagram")) { ... }
                // if (site.getName().equalsIgnoreCase("TikTok")) { ... }
            }

            return Arrays.asList(sites);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load sites.json", e);
        }
    }
}
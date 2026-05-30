package com.milly.osint.core;

import com.milly.osint.http.HttpClientProvider;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScannerService {

    public List<ScanResult> scanUsername(String username) {
        List<SiteDefinition> sites = SiteLoader.loadSites();
        List<ScanResult> results = new ArrayList<>();

        for (SiteDefinition site : sites) {
            ScanResult result = scanSite(site, username);
            results.add(result);
        }

        return results;
    }

    private ScanResult scanSite(SiteDefinition site, String username) {
        String url = site.getUrl().replace("{username}", username);

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClientProvider.get()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            boolean exists = response.statusCode() == site.getExistsWhenStatus();

            return new ScanResult(site.getName(), exists, url, Map.of());

        } catch (Exception e) {
            return new ScanResult(site.getName(), false, url, Map.of());
        }
    }

}

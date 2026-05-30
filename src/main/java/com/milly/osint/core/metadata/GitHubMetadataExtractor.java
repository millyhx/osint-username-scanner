package com.milly.osint.core.metadata;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.milly.osint.core.MetadataExtractor;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import static com.milly.osint.http.HttpClientProvider.get;

public class GitHubMetadataExtractor implements MetadataExtractor {

    @Override
    public Map<String, String> extract(String username) {
        Map<String, String> data = new HashMap<>();

        try {
            String apiUrl = "https://api.github.com/users/" + username;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .GET()
                    .build();

            HttpResponse<String> response = get()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(response.body());

            data.put("name", node.path("name").asText(""));
            data.put("bio", node.path("bio").asText(""));
            data.put("followers", node.path("followers").asText(""));
            data.put("following", node.path("following").asText(""));
            data.put("public_repos", node.path("public_repos").asText(""));
            data.put("avatar", node.path("avatar_url").asText(""));

        } catch (Exception ignored) {}

        return data;
    }
}

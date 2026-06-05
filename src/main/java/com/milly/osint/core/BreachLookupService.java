package com.milly.osint.core;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BreachLookupService {

    private static final String API_URL =
            "https://api.breaches.dev/api/v1/breaches?query=";

    public List<BreachEntry> search(String query) throws Exception {
        List<BreachEntry> results = new ArrayList<>();

        HttpURLConnection conn = (HttpURLConnection) new URL(API_URL + query).openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();

        String response = sb.toString().trim();

        if (!response.startsWith("{")) {
            throw new Exception("Unexpected response: " + response);
        }

        JSONObject json = new JSONObject(response);
        JSONArray breaches = json.optJSONArray("breaches");

        if (breaches == null) {
            return results;
        }

        for (int i = 0; i < breaches.length(); i++) {
            JSONObject obj = breaches.getJSONObject(i);

            results.add(new BreachEntry(
                    obj.optString("name", "Unknown"),
                    obj.optString("email", query),
                    obj.optString("password", "")
            ));
        }

        return results;
    }
}

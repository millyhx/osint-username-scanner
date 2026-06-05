package com.milly.osint.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UsernameIntelligenceService {

    private final Set<String> nameDictionary = new HashSet<>();

    public UsernameIntelligenceService() {
        loadNameDictionary();
    }

    private void loadNameDictionary() {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            getClass().getResourceAsStream("/names.txt")
                    )
            );

            String line;
            while ((line = reader.readLine()) != null) {
                nameDictionary.add(line.trim().toLowerCase());
            }

            reader.close();
        } catch (Exception e) {
            System.err.println("Failed to load name dictionary: " + e.getMessage());
        }
    }

    public List<UsernameIntelligenceResult> analyse(String username) {
        List<UsernameIntelligenceResult> results = new ArrayList<>();
        String lower = username.toLowerCase();

        // 1. Name detection
        for (String name : nameDictionary) {
            if (lower.contains(name)) {
                results.add(new UsernameIntelligenceResult(
                        "Possible Name",
                        "Contains the name: " + name
                ));
                break; // avoid spamming multiple names
            }
        }

        // 2. Birth year detection
        if (lower.matches(".*(19[7-9][0-9]|20[0-2][0-9]).*")) {
            results.add(new UsernameIntelligenceResult(
                    "Possible Birth Year",
                    "Contains a year-like number (1970–2029)"
            ));
        }

        // 3. Numbers
        if (lower.matches(".*\\d+.*")) {
            results.add(new UsernameIntelligenceResult(
                    "Numbers Detected",
                    "Username includes numeric characters"
            ));
        }

        // 4. Location hints
        if (lower.matches(".*(uk|usa|ldn|bristol|nyc|manchester|scotland).*")) {
            results.add(new UsernameIntelligenceResult(
                    "Possible Location",
                    "Contains a location keyword"
            ));
        }

        // 5. Hobbies
        if (lower.matches(".*(gamer|dev|coder|chef|baker|artist|music|photo).*")) {
            results.add(new UsernameIntelligenceResult(
                    "Possible Hobby",
                    "Contains a hobby or interest keyword"
            ));
        }

        // 6. Brand-like patterns
        if (lower.matches(".*(official|real|the|hq|inc|corp).*")) {
            results.add(new UsernameIntelligenceResult(
                    "Brand Pattern",
                    "Looks like a brand or public-facing username"
            ));
        }

        // 7. Separators
        if (username.contains("_") || username.contains(".") || username.contains("-")) {
            results.add(new UsernameIntelligenceResult(
                    "Separators",
                    "Uses separators such as underscore, dot, or hyphen"
            ));
        }

        // 8. Length analysis
        if (username.length() <= 5) {
            results.add(new UsernameIntelligenceResult(
                    "Short Username",
                    "Short usernames are often early adopters or high-value"
            ));
        }

        if (username.length() >= 15) {
            results.add(new UsernameIntelligenceResult(
                    "Long Username",
                    "Long usernames may indicate uniqueness or low availability"
            ));
        }

        return results;
    }
}

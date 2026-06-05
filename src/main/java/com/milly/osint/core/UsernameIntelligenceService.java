package com.milly.osint.core;

import java.util.ArrayList;
import java.util.List;

public class UsernameIntelligenceService {

    public List<UsernameIntelligenceResult> analyse(String username) {
        List<UsernameIntelligenceResult> results = new ArrayList<>();

        String lower = username.toLowerCase();

        // Detect possible name
        if (lower.matches(".*(milly|ben|john|emma|alex|sam|chris|kate|lucy).*")) {
            results.add(new UsernameIntelligenceResult(
                    "Possible Name",
                    "Username contains what looks like a first name"
            ));
        }

        // Detect birth year
        if (lower.matches(".*(19[7-9][0-9]|20[0-2][0-9]).*")) {
            results.add(new UsernameIntelligenceResult(
                    "Possible Birth Year",
                    "Contains a year-like number (1970–2029)"
            ));
        }

        // Detect numbers
        if (lower.matches(".*\\d+.*")) {
            results.add(new UsernameIntelligenceResult(
                    "Numbers Detected",
                    "Username includes numeric characters"
            ));
        }

        // Detect location hints
        if (lower.matches(".*(uk|usa|ldn|bristol|nyc|manchester|scotland).*")) {
            results.add(new UsernameIntelligenceResult(
                    "Possible Location",
                    "Contains a location keyword"
            ));
        }

        // Detect hobby keywords
        if (lower.matches(".*(gamer|dev|coder|chef|baker|artist|music|photo).*")) {
            results.add(new UsernameIntelligenceResult(
                    "Possible Hobby",
                    "Username contains a hobby or interest"
            ));
        }

        // Detect brand-like patterns
        if (lower.matches(".*(official|real|the|hq|inc|corp).*")) {
            results.add(new UsernameIntelligenceResult(
                    "Brand Pattern",
                    "Looks like a brand or public-facing username"
            ));
        }

        // Detect underscores / separators
        if (username.contains("_") || username.contains(".") || username.contains("-")) {
            results.add(new UsernameIntelligenceResult(
                    "Separators",
                    "Username uses separators (._-)"
            ));
        }

        // Detect short usernames
        if (username.length() <= 5) {
            results.add(new UsernameIntelligenceResult(
                    "Short Username",
                    "Short usernames are often early adopters or high-value"
            ));
        }

        // Detect long usernames
        if (username.length() >= 15) {
            results.add(new UsernameIntelligenceResult(
                    "Long Username",
                    "Long usernames may indicate uniqueness or low availability"
            ));
        }

        return results;
    }
}

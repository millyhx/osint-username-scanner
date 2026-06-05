package com.milly.osint.core;

public class UsernameIntelligenceResult {

    private final String category;
    private final String detail;

    public UsernameIntelligenceResult(String category, String detail) {
        this.category = category;
        this.detail = detail;
    }

    public String getCategory() {
        return category;
    }

    public String getDetail() {
        return detail;
    }
}

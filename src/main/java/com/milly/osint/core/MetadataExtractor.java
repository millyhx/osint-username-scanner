package com.milly.osint.core;

import java.util.Map;

public interface MetadataExtractor {
    Map<String, String> extract(String responseBody);
}

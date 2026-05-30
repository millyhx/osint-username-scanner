package com.milly.osint.http;

import java.net.http.HttpClient;

public class HttpClientProvider {

    private static final HttpClient client = HttpClient.newHttpClient();

    public static HttpClient get() {
        return client;
    }
}

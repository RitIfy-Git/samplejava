package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProximusScraper {

    private static final String TARGET_URL = "https://www.proximus.be/en/personal/";

    public static void main(String[] args) {
        try {
            String html = fetchPage(TARGET_URL);
            System.out.println("Fetched HTML content:");
            System.out.println(html.substring(0, Math.min(html.length(), 1000)) + "...");
        } catch (IOException e) {
            System.err.println("Error fetching page: " + e.getMessage());
        }
    }

    public static String fetchPage(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = connection.getResponseCode();

        if (responseCode != 200) {
            throw new IOException("Failed to fetch page, HTTP response code: " + responseCode);
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {

            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }

            return content.toString();
        }
    }
}

package com.freshlokal.backend.com.freshlokal.backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.OutputStream;

public class Search implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Content-Type", "application/json");

            // Get query parameters (search by name)
            String query = exchange.getRequestURI().getQuery();  // Expecting something like: name=Apple
            String[] queryParams = query.split("="); // Splitting by '='
            String productName = queryParams[1];  // Extract product name

            List<String[]> products = readProductsFromCSV();
            List<String[]> searchResults = new ArrayList<>();

            // Search for products that match the name
            for (String[] product : products) {
                if (product[1].equalsIgnoreCase(productName)) {
                    searchResults.add(product);
                }
            }

            // Generate JSON response
            String response = generateJsonResponse(searchResults);

            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method not allowed
        }
    }

    // Method to read products from CSV
    private List<String[]> readProductsFromCSV() {
        List<String[]> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("products.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                products.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    // Method to generate JSON response for search results
    private String generateJsonResponse(List<String[]> searchResults) {
        StringBuilder jsonResponse = new StringBuilder("[");
        for (int i = 0; i < searchResults.size(); i++) {
            String[] product = searchResults.get(i);
            jsonResponse.append("{");
            jsonResponse.append("\"category\": \"").append(product[0]).append("\", ");
            jsonResponse.append("\"name\": \"").append(product[1]).append("\", ");
            jsonResponse.append("\"price\": \"").append(product[2]).append("\", ");
            jsonResponse.append("\"description\": \"").append(product[3]).append("\"");
            jsonResponse.append("}");
            if (i < searchResults.size() - 1) {
                jsonResponse.append(", ");
            }
        }
        jsonResponse.append("]");
        return jsonResponse.toString();
    }
}

package com.freshlokal.backend;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class CartHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Content-Type", "application/json");

            // Get the currently logged-in user
            String currentUser = CSVUtils.getCurrentUser();
            if (currentUser == null || currentUser.isEmpty()) {
                String response = "{\"message\": \"User not logged in. Please sign in first.\"}";
                exchange.sendResponseHeaders(401, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
                return;
            }

            // Define the user's cart file
            String userCartFile = "Database/" + currentUser + "_cart.csv";

            // Read cart items
            List<String[]> cartItems = CSVUtils.readCartCSV(userCartFile);
            String jsonResponse = CSVUtils.convertToJson(cartItems);

            exchange.sendResponseHeaders(200, jsonResponse.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(jsonResponse.getBytes());
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method not allowed
        }
    }
}

package com.freshlokal.backend;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class TPriceHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Content-Type", "application/json");

            // Get the logged-in user
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

            // Calculate the total price for the logged-in user
            double totalPrice = CSVUtils.calculateTotalPrice(userCartFile);

            // Prepare JSON response
            String response = "{\"totalPrice\": " + totalPrice + "}";
            exchange.sendResponseHeaders(200, response.getBytes().length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method not allowed
        }
    }
}

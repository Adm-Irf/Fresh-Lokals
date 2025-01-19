package com.freshlokal.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class PurchaseHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // ✅ Handle Preflight (CORS) Requests
        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
            exchange.sendResponseHeaders(204, -1); // No Content response
            return;
        }

        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
            JsonObject requestBody = new Gson().fromJson(reader, JsonObject.class);
            reader.close();

            String transactionId = requestBody.get("transactionId").getAsString();
            String currentUser = CSVUtils.getCurrentUser();
            
            if (currentUser == null || currentUser.isEmpty()) {
                sendResponse(exchange, 401, "{\"message\": \"User not logged in. Please sign in first.\"}");
                return;
            }

            List<String[]> cartItems = CSVUtils.readCartCSV("Database/" + currentUser + "_cart.csv");

            if (cartItems.isEmpty()) {
                sendResponse(exchange, 400, "{\"message\": \"Cart is empty. Nothing to purchase.\"}");
                return;
            }

            // ✅ Append all cart items with the correct quantity field
            for (String[] item : cartItems) {
                if (item.length < 6) { 
                    sendResponse(exchange, 500, "{\"message\": \"Error: Cart data is incomplete!\"}");
                    return;
                }

                // ✅ Correct order for CSV storage
                String[] purchaseEntry = { 
                    transactionId, // ✅ Transaction ID
                    item[0],  // ✅ Category
                    item[1],  // ✅ Product Name
                    item[2],  // ✅ Price
                    item[3],  // ✅ Description
                    item[4],  // ✅ Image
                    item[5]   // ✅ Quantity (Ensure it's correct)
                };                

                // ✅ Save purchase to file
                CSVUtils.appendToPurchaseCSV(purchaseEntry, "Database/" + currentUser + "_purchase.csv");
            }

            // ✅ Clear cart after purchase
            CSVUtils.clearFile("Database/" + currentUser + "_cart.csv");

            sendResponse(exchange, 200, "{\"message\": \"Purchase recorded successfully!\"}");
        
        } else if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            // ✅ Fetch the user's purchase history
            String currentUser = CSVUtils.getCurrentUser();
            
            if (currentUser == null || currentUser.isEmpty()) {
                sendResponse(exchange, 401, "{\"message\": \"User not logged in. Please sign in first.\"}");
                return;
            }

            List<String[]> purchases = CSVUtils.readCartCSV("Database/" + currentUser + "_purchase.csv");
            
            if (purchases.isEmpty()) {
                sendResponse(exchange, 200, "[]"); // ✅ Return empty array instead of message
                return;
            }

            // ✅ Convert to JSON format
            String jsonResponse = new Gson().toJson(purchases);

            sendResponse(exchange, 200, jsonResponse);

        } else {
            sendResponse(exchange, 405, "{\"message\": \"Method not allowed.\"}");
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String message) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
        exchange.sendResponseHeaders(statusCode, message.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(message.getBytes());
        }
    }
}

package com.freshlokal.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

public class ProductHandler {
    public static void registerEndpoints(HttpServer server) {
        // Endpoint to fetch products
        server.createContext("/products", ProductHandler::handleGetProducts);
    }
    
    public static void handleGetProducts(HttpExchange exchange) throws IOException {
        // Existing code
        System.out.println("GET /products endpoint called");
        if ("GET".equals(exchange.getRequestMethod())) {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Content-Type", "application/json");
    
            // Read products from CSV
            List<String[]> products = CSVUtils.readCSV();
            String jsonResponse = CSVUtils.convertToJson(products);
    
            exchange.sendResponseHeaders(200, jsonResponse.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(jsonResponse.getBytes());
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method not allowed
        }
    }
    

    public static void handleAddToCart(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Content-Type", "application/json");
    
            // Check if a user is logged in
            String currentUser = CSVUtils.getCurrentUser();
            if (currentUser == null || currentUser.isEmpty()) {
                String response = "{\"message\": \"User not logged in. Please sign in first.\"}";
                exchange.sendResponseHeaders(401, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
                return;
            }
    
            // Read request body (productName,quantity)
            BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
            String requestBody = reader.readLine();
            reader.close();
    
            String[] input = requestBody.split(",", 2);
            if (input.length != 2) {
                exchange.sendResponseHeaders(400, -1); // Bad Request
                return;
            }
    
            String productName = input[0].trim();
            int quantity;
            try {
                quantity = Integer.parseInt(input[1].trim());
            } catch (NumberFormatException e) {
                exchange.sendResponseHeaders(400, -1); // Bad Request
                return;
            }
    
            // Find the product in Products.csv
            List<String[]> products = CSVUtils.readCSV();
            String[] selectedProduct = null;
            for (String[] product : products) {
                if (product[1].equalsIgnoreCase(productName)) {
                    selectedProduct = product;
                    break;
                }
            }
    
            if (selectedProduct != null) {
                // Define the user's cart file
                String userCartFile = "Database/" + currentUser + "_cart.csv";
    
                // Append the product to the user's cart
                String[] cartEntry = {
                    selectedProduct[0], // Category
                    selectedProduct[1], // Name
                    selectedProduct[2], // Price per unit
                    selectedProduct[3], // Description
                    String.valueOf(quantity) // Quantity
                };
                CSVUtils.appendToUserCart(cartEntry, userCartFile);
    
                // Send success response
                String response = "{\"message\": \"Item added to cart successfully!\"}";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } else {
                // Product not found
                String response = "{\"message\": \"Product not found!\"}";
                exchange.sendResponseHeaders(404, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method not allowed
        }
    }    

 }

    

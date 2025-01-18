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
        System.out.println("GET /products called");
    
        if ("GET".equals(exchange.getRequestMethod())) {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Content-Type", "application/json");
    
            // ✅ Ensure user is signed in before viewing products
            String currentUser = CSVUtils.getCurrentUser();
            if (currentUser == null || currentUser.isEmpty()) {
                String response = "{\"message\": \"User not logged in. Please sign in first.\"}";
                exchange.sendResponseHeaders(401, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
                return;
            }
    
            // ✅ Load products from CSV
            List<String[]> products = CSVUtils.readCSV();
            if (products.isEmpty()) {
                String response = "[]"; // Return empty array if no products
                exchange.sendResponseHeaders(200, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
                return;
            }
    
            String jsonResponse = CSVUtils.convertProductsToJson(products); // ✅ Use correct function
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
    
            // Read request body (productName, quantity)
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
    
                // ✅ Ensure we write the image field correctly
                String[] cartEntry = {
                    selectedProduct[0], // Category
                    selectedProduct[1], // Name
                    selectedProduct[2], // Price per unit
                    selectedProduct[3], // Description
                    selectedProduct[4], // ✅ Image field (previously missing)
                    String.valueOf(quantity) // ✅ Quantity
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
    
    
    private static void sendResponse(HttpExchange exchange, int statusCode, String message) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, message.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(message.getBytes());
        }
    }

    public static void handleDeleteFromCart(HttpExchange exchange) throws IOException {
        if ("DELETE".equals(exchange.getRequestMethod())) {
            // ✅ Add CORS headers to allow frontend requests
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "DELETE, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
            exchange.getResponseHeaders().add("Content-Type", "application/json");
    
            System.out.println("DELETE request received for cart");
    
            String currentUser = CSVUtils.getCurrentUser();
            if (currentUser == null || currentUser.isEmpty()) {
                String response = "{\"message\": \"User not logged in. Please sign in first.\"}";
                exchange.sendResponseHeaders(401, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
                return;
            }
    
            // Read request body
            BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
            String productName = reader.readLine();
            reader.close();
    
            System.out.println("Deleting from cart: " + productName);
    
            // User's cart file
            String userCartFile = "Database/" + currentUser + "_cart.csv";
    
            boolean deleted = CSVUtils.deleteFromCart(userCartFile, productName);
            if (deleted) {
                String response = "{\"message\": \"Item removed from cart successfully!\"}";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } else {
                String response = "{\"message\": \"Item not found in cart.\"}";
                exchange.sendResponseHeaders(404, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            }
        } else if ("OPTIONS".equals(exchange.getRequestMethod())) { // ✅ Handle preflight request
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "DELETE, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
            exchange.sendResponseHeaders(204, -1); // No content for preflight request
        } else {
            exchange.sendResponseHeaders(405, -1); // Method not allowed
        }
    }
    
    
    public static void handleGetItemTotalPrice(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Content-Type", "application/json");
    
            // Get the logged-in user
            String currentUser = CSVUtils.getCurrentUser();
            if (currentUser == null || currentUser.isEmpty()) {
                sendResponse(exchange, 401, "{\"message\": \"User not logged in. Please sign in first.\"}");
                return;
            }
    
            // Read request body to get product name
            BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
            String productName = reader.readLine();
            reader.close();
    
            // Define the user's cart file
            String userCartFile = "Database/" + currentUser + "_cart.csv";
    
            // Calculate the total price for the item
            double itemTotalPrice = CSVUtils.calculateItemTotalPrice(userCartFile, productName);
    
            if (itemTotalPrice >= 0) {
                String response = "{\"product\": \"" + productName + "\", \"totalPrice\": " + itemTotalPrice + "}";
                sendResponse(exchange, 200, response);
            } else {
                sendResponse(exchange, 404, "{\"message\": \"Product not found in cart!\"}");
            }
        } else {
            sendResponse(exchange, 405, "{\"message\": \"Method not allowed.\"}");
        }
    }

    public static void handleGetCart(HttpExchange exchange) throws IOException {
        System.out.println("🚀 GET /userCart called");
    
        if ("GET".equals(exchange.getRequestMethod())) {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Content-Type", "application/json");
    
            // Ensure user is logged in
            String currentUser = CSVUtils.getCurrentUser();
            if (currentUser == null || currentUser.isEmpty()) {
                String response = "{\"message\": \"User not logged in. Please sign in first.\"}";
                System.out.println("❌ User not logged in");
                exchange.sendResponseHeaders(401, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
                return;
            }
    
            // Read cart data
            String userCartFile = "Database/" + currentUser + "_cart.csv";
            List<String[]> cartItems = CSVUtils.readCartCSV(userCartFile);
    
            System.out.println("📦 Cart Items Loaded: " + cartItems.size());
    
            if (cartItems.isEmpty()) {
                String response = "[]"; // Empty cart
                exchange.sendResponseHeaders(200, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
                return;
            }
    
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

    

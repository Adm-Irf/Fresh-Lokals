package com.freshlokal.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

public class ProductHandler {
    public static void registerEndpoints(HttpServer server) {
        // Endpoint to fetch products
        server.createContext("/products", ProductHandler::handleGetProducts);

        // Endpoint to add a product
        server.createContext("/addProduct", ProductHandler::handleAddProduct);
        
        server.createContext("/deleteProduct", ProductHandler::handleDeleteProduct);
    }

    public static void handleDeleteProduct(HttpExchange exchange) throws IOException {
        if ("DELETE".equals(exchange.getRequestMethod())) {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Content-Type", "application/json");
    
            // Read the product name from the request body
            InputStream inputStream = exchange.getRequestBody();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String productName = reader.readLine();
    
            // Delete the product from the CSV file
            boolean isDeleted = CSVUtils.deleteFromCSV(productName);
    
            String response;
            if (isDeleted) {
                response = "{\"message\": \"Product deleted successfully!\"}";
                exchange.sendResponseHeaders(200, response.getBytes().length);
            } else {
                response = "{\"message\": \"Product not found!\"}";
                exchange.sendResponseHeaders(404, response.getBytes().length);
            }
    
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method not allowed
        }
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
    
    public static void handleAddProduct(HttpExchange exchange) throws IOException {
        // Existing code
        System.out.println("POST /addProduct endpoint called");
        if ("POST".equals(exchange.getRequestMethod())) {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Content-Type", "application/json");
    
            // Read the request body
            InputStream inputStream = exchange.getRequestBody();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = reader.readLine();
            String[] newProduct = line.split(",", 4); // Expecting a CSV-like format in the body
    
            // Add the new product to the CSV file
            CSVUtils.appendToCSV(newProduct);
    
            String response = "{\"message\": \"Product added successfully!\"}";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method not allowed
        }
    }

    public static void handleAddToCart(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Content-Type", "application/json");
    
            // Read the request body
            InputStream inputStream = exchange.getRequestBody();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String requestBody = reader.readLine(); // Expecting: "productName,quantity"
    
            // Parse product name and quantity
            String[] input = requestBody.split(",", 2);
            String productName = input[0].trim();
            int quantity = Integer.parseInt(input[1].trim());
    
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
                // Append product to User1cart.csv
                String[] cartEntry = {
                    selectedProduct[0], // Category
                    selectedProduct[1], // Name
                    selectedProduct[2], // Price per unit
                    selectedProduct[3], // Description
                    String.valueOf(quantity) // Quantity
                };
                CSVUtils.appendToCart(cartEntry);
    
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

    

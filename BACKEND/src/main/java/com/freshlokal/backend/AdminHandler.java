package com.freshlokal.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class AdminHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            handlePreflight(exchange);
            return;
        }

        if (!CSVUtils.isAdmin()) {
            sendResponse(exchange, 403, "{\"message\": \"Access denied. Admin only.\"}");
            return;
        }
        
        
    
        String path = exchange.getRequestURI().getPath();
        System.out.println("Admin Request Path: " + path);

        if (path.equals("/admin/viewProducts") && "GET".equals(exchange.getRequestMethod())) {
            handleViewProducts(exchange);
        } else if (path.equals("/admin/viewUsers") && "GET".equals(exchange.getRequestMethod())) {
            handleViewUsers(exchange);
        } else if (path.equals("/admin/addProduct") && "POST".equals(exchange.getRequestMethod())) {
            handleAddProduct(exchange);
        } else if (path.equals("/admin/deleteProduct") && "DELETE".equals(exchange.getRequestMethod())) {
            handleDeleteProduct(exchange);
        } else if (path.equals("/admin/deleteUser") && "DELETE".equals(exchange.getRequestMethod())) {
            handleDeleteUser(exchange);
        } else if (path.equals("/admin/updateProduct") && "POST".equals(exchange.getRequestMethod())) {
                handleUpdateProduct(exchange);          
        } else {
            sendResponse(exchange, 405, "{\"message\": \"Method not allowed.\"}");
        }
    }

    private void handlePreflight(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type, Authorization");
        exchange.sendResponseHeaders(204, -1); // No content for preflight response
    }

    private void handleAddProduct(HttpExchange exchange) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        String requestBody = reader.readLine();
        reader.close();
    
        System.out.println("Admin Adding Product: " + requestBody);
    
        String[] newProduct = requestBody.split(",", 5);  // ✅ Update to 5 fields (including image)
        if (newProduct.length != 5) {
            sendResponse(exchange, 400, "{\"message\": \"Invalid product format. Expected: category,name,price,description,imagePath\"}");
            return;
        }
    
        CSVUtils.appendToCSV(newProduct);
        sendResponse(exchange, 200, "{\"message\": \"Product added successfully!\"}");
    }
    

    private void handleDeleteProduct(HttpExchange exchange) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        String productName = reader.readLine();
        reader.close();

        boolean deleted = CSVUtils.deleteFromCSV(productName);
        if (deleted) {
            sendResponse(exchange, 200, "{\"message\": \"Product deleted successfully!\"}");
        } else {
            sendResponse(exchange, 404, "{\"message\": \"Product not found!\"}");
        }
    }

    private void handleDeleteUser(HttpExchange exchange) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        String usernameToDelete = reader.readLine();
        reader.close();

        boolean deleted = CSVUtils.deleteUser(usernameToDelete);
        if (deleted) {
            sendResponse(exchange, 200, "{\"message\": \"User deleted successfully!\"}");
        } else {
            sendResponse(exchange, 404, "{\"message\": \"User not found!\"}");
        }
    }

    private static final String UPLOAD_DIR = "C:/Users/Irfan/OneDrive/Desktop/FRESHLOKAL/uploads/";

     


    private void handleUpdateProduct(HttpExchange exchange) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        String requestBody = reader.readLine();
        reader.close();

        System.out.println("Admin Updating Product: " + requestBody);

        String[] updateData = requestBody.split(",", 5);
        if (updateData.length != 5) {
            sendResponse(exchange, 400, "{\"message\": \"Invalid update format. Use: oldProductName,newCategory,newName,newPrice,newDescription\"}");
            return;
        }

        String oldProductName = updateData[0].trim();
        String newCategory = updateData[1].trim();
        String newName = updateData[2].trim();
        String newPrice = updateData[3].trim();
        String newDescription = updateData[4].trim();

        boolean updated = CSVUtils.updateProduct(oldProductName, newCategory, newName, newPrice, newDescription);
        if (updated) {
            sendResponse(exchange, 200, "{\"message\": \"Product updated successfully!\"}");
        } else {
            sendResponse(exchange, 404, "{\"message\": \"Product not found!\"}");
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String message) throws IOException {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type, Authorization");
        exchange.sendResponseHeaders(statusCode, message.getBytes().length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(message.getBytes());
        }
    }

    private void handleViewProducts(HttpExchange exchange) throws IOException {
    List<String[]> products = CSVUtils.readCSV(); // Read products from CSV

    // Convert products to the expected JSON structure
    List<Map<String, String>> productList = new ArrayList<>();
    
    for (String[] product : products) {
        if (product.length < 5) {
            System.out.println("⚠ Skipping invalid product: " + Arrays.toString(product));
            continue;
        }

        Map<String, String> productMap = new HashMap<>();
        productMap.put("category", product[0]);
        productMap.put("name", product[1]);
        productMap.put("price", product[2]);
        productMap.put("description", product[3]);
        productMap.put("image", product[4]); // Ensure image field is included

        productList.add(productMap);
    }

    String jsonResponse = new Gson().toJson(productList);
    
    // Debugging: Print the JSON response before sending
    System.out.println("✅ Admin JSON Response: " + jsonResponse);

    sendResponse(exchange, 200, jsonResponse);
}

    


    private void handleViewUsers(HttpExchange exchange) throws IOException {
        List<String[]> users = CSVUtils.readUsersWithPasswords();
        String jsonResponse = CSVUtils.convertUsersToJson(users);

        sendResponse(exchange, 200, jsonResponse);
    }

    
}
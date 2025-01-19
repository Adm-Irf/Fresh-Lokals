package com.freshlokal.backend;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

public class App {
    public static void main(String[] args) throws IOException {
        CSVUtils.checkUsersFile();
        CSVUtils.testReadUsersFile();
        // Create HTTP server listening on port 8080
    
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/admin/updateProduct", exchange -> {
            addCorsHeaders(exchange);
            new AdminHandler().handle(exchange);
        });
        server.createContext("/admin/addProduct", new AdminHandler());
        server.createContext("/admin/deleteProduct", new AdminHandler());
        server.createContext("/admin/deleteUser", new AdminHandler());
        server.createContext("/admin/viewProducts", exchange -> {
            addCorsHeaders(exchange);
            new AdminHandler().handle(exchange);
        });
        
        server.createContext("/admin/viewUsers", exchange -> {
            addCorsHeaders(exchange);
            new AdminHandler().handle(exchange);
        });
        
        server.createContext("/userCart", new CartHandler());
        server.createContext("/removeFromCart", exchange -> {
            addCorsHeaders(exchange);  // ✅ Add CORS headers
            ProductHandler.handleDeleteFromCart(exchange);
        });        
        server.createContext("/login", new AuthHandler());
        server.createContext("/signup", new SignupHandler());
        server.createContext("/currentUser", exchange -> {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Content-Type", "application/json");
        
            String currentUser = CSVUtils.getCurrentUser();
            String response;
            if (currentUser == null || currentUser.isEmpty()) {
                response = "{\"currentUser\": null}";
                exchange.sendResponseHeaders(404, response.getBytes().length);
            } else {
                response = "{\"currentUser\": \"" + currentUser + "\"}";
                exchange.sendResponseHeaders(200, response.getBytes().length);
            }
        
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        });

        server.createContext("/logout", exchange -> {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
        
            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1); // Preflight request response
                return;
            }
        
            CSVUtils.logoutUser(); // ✅ Clears session
        
            String response = "{\"message\": \"Logged out successfully\"}";
            exchange.sendResponseHeaders(200, response.getBytes().length);
        
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        });
        
        server.createContext("/itemTotalPrice", ProductHandler::handleGetItemTotalPrice);
        server.createContext("/admin/uploadProduct", new AdminHandler()); // ✅ Corrected

        System.out.println("Registering /products endpoint...");
        server.createContext("/products", ProductHandler::handleGetProducts);
        server.createContext("/deleteFromCart", ProductHandler::handleDeleteFromCart);
        server.createContext("/totalPrice", new TPriceHandler());
        server.createContext("/purchase", new PurchaseHandler());
        System.out.println("Registering /addToCart endpoint...");
        server.createContext("/addToCart", ProductHandler::handleAddToCart);

        server.createContext("/checkout", new PaymentHandler());
        

        // Serve static images
        server.createContext("/images", exchange -> {
            String filePath = "Database" + exchange.getRequestURI().getPath(); // Load from Database/images/
            File file = new File(filePath);
            if (file.exists()) {
                exchange.sendResponseHeaders(200, file.length());
                OutputStream os = exchange.getResponseBody();
                Files.copy(file.toPath(), os);
                os.close();
            } else {
                exchange.sendResponseHeaders(404, -1);
            }
        });     

        // Start the server
        server.setExecutor(null); // Default executor
        server.start();
        System.out.println("Server started at http://localhost:8080");
    }
    
    // ✅ Function to add CORS headers
    private static void addCorsHeaders(HttpExchange exchange) {
        if (!exchange.getResponseHeaders().containsKey("Access-Control-Allow-Origin")) {
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*"); 
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS");
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type, Authorization");
        }
    }
    
}

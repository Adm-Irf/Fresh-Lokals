package com.freshlokal.backend;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class App {
    public static void main(String[] args) throws IOException {
        CSVUtils.checkUsersFile();
        CSVUtils.testReadUsersFile();
        // Create HTTP server listening on port 8080
    
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/admin/updateProduct", new AdminHandler());
        server.createContext("/admin/addProduct", new AdminHandler());
        server.createContext("/admin/deleteProduct", new AdminHandler());
        server.createContext("/admin/deleteUser", new AdminHandler());
        server.createContext("/admin/viewProducts", new AdminHandler());
        server.createContext("/admin/viewUsers", new AdminHandler());

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
            exchange.getResponseHeaders().add("Content-Type", "application/json");
        
            CSVUtils.logoutUser(); // Call the function to clear user session
        
            String response = "{\"message\": \"Logged out successfully\"}";
            exchange.sendResponseHeaders(200, response.getBytes().length);
        
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        });

        // Register endpoints
        System.out.println("Registering /products endpoint...");
        server.createContext("/products", ProductHandler::handleGetProducts);

        server.createContext("/totalPrice", new TPriceHandler());

        System.out.println("Registering /addToCart endpoint...");
        server.createContext("/addToCart", ProductHandler::handleAddToCart);

        server.createContext("/checkout", new PaymentHandler());


        // Define endpoint `/compute`
        server.createContext("/compute", exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, OPTIONS");
                exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

                String response = "{\"message\": \"Hello from Maven-based backend!\", \"result\": " + (2 + 2) + "}";

                // Add response headers
                exchange.getResponseHeaders().add("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.getBytes().length);

                // Send response
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } else {
                exchange.sendResponseHeaders(405, -1); // Method not allowed
            }
        });

        // Start the server
        server.setExecutor(null); // Default executor
        server.start();
        System.out.println("Server started at http://localhost:8080");
    }
}

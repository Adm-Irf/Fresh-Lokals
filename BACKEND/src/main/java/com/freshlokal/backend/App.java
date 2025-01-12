package com.freshlokal.backend;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class App {
    public static void main(String[] args) throws IOException {
        // Create HTTP server listening on port 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

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

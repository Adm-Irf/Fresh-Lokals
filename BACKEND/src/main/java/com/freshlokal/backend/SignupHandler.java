package com.freshlokal.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class SignupHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
            String requestBody = reader.readLine();
            reader.close();

            // Expecting input format: "username,password"
            String[] credentials = requestBody.split(",");
            if (credentials.length != 2) {
                exchange.sendResponseHeaders(400, -1); // Bad Request
                return;
            }

            String username = credentials[0].trim();
            String password = credentials[1].trim();

            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Content-Type", "application/json");

            String response;
            if (CSVUtils.registerUser(username, password)) {
                response = "{\"message\": \"Sign-up successful! Welcome, " + username + "\"}";
                exchange.sendResponseHeaders(200, response.getBytes().length);
            } else {
                response = "{\"message\": \"Username already exists. Choose a different one.\"}";
                exchange.sendResponseHeaders(409, response.getBytes().length); // Conflict
            }

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }
}

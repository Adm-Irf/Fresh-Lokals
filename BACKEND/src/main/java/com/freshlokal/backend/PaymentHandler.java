package com.freshlokal.backend;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;

public class PaymentHandler implements HttpHandler {

    // This is the method that should be called by the server
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            // Example: Process payment and send response
            String response = "{\"message\": \"Payment created successfully\"}";

            // Set response headers and send the response body
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.getBytes().length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } else {
            // Respond with a 405 if it's not a POST request
            exchange.sendResponseHeaders(405, -1);
        }
    }
}

package com.freshlokal.backend;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import com.sun.net.httpserver.HttpExchange;

public class PaymentHandler {
    public static void handlePayment(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            // Simulate total price calculation (you can fetch this from TPriceHandler)
            double totalPrice = 100.50; // Replace with dynamic calculation
            String transactionId = UUID.randomUUID().toString();

            // Simulate payment initiation
            String paymentUrl = String.format(
                    "https://mockpaymentgateway.com/pay?transactionId=%s&amount=%.2f",
                    transactionId, totalPrice
            );

            // Prepare JSON response
            String response = String.format(
                    "{\"transactionId\": \"%s\", \"paymentUrl\": \"%s\", \"totalPrice\": %.2f}",
                    transactionId, paymentUrl, totalPrice
            );

            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.getBytes().length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method not allowed
        }
    }
}
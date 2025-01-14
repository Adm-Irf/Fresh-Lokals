package com.freshlokal.backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class TPriceHandler implements HttpHandler {
    private static final String CART_CSV_PATH = "C:\\Users\\Irfan\\OneDrive\\Desktop\\FRESHLOKAL\\BACKEND\\Database\\User1cart.csv";

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Content-Type", "application/json");

            // Read the cart and calculate total price
            List<String[]> cartItems = readCartCSV();
            double totalPrice = calculateTotalPrice(cartItems);

            // Prepare JSON response
            String response = "{\"totalPrice\": " + totalPrice + "}";
            exchange.sendResponseHeaders(200, response.getBytes().length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method not allowed
        }
    }

    // Method to read the cart CSV file
    private List<String[]> readCartCSV() {
        List<String[]> cartItems = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CART_CSV_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                cartItems.add(line.split(",", 5)); // Split into 5 fields
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    // Method to calculate the total price from the cart
    private double calculateTotalPrice(List<String[]> cartItems) {
        double totalPrice = 0;
        for (String[] item : cartItems) {
            try {
                double pricePerUnit = Double.parseDouble(item[2]); // Price field
                int quantity = Integer.parseInt(item[4]);          // Quantity field
                totalPrice += pricePerUnit * quantity;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format in cart: " + String.join(",", item));
            }
        }
        return totalPrice;
    }
}

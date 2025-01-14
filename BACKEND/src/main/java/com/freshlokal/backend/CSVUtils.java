package com.freshlokal.backend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVUtils {
    private static final String CSV_FILE_PATH = "C:\\\\Users\\\\Irfan\\\\OneDrive\\\\Desktop\\\\FRESHLOKAL\\\\BACKEND\\\\Database\\\\Products.csv";

    public static void main(String[] args) {
        File file = new File(CSV_FILE_PATH);
        System.out.println("File exists: " + file.exists());
        System.out.println("File path: " + file.getAbsolutePath());
    
        List<String[]> products = readCSV();
        if (products.isEmpty()) {
            System.out.println("No products found or failed to read CSV.");
        } else {
            for (String[] product : products) {
                System.out.println(String.join(", ", product));
            }
        }
    }

    // Method to read CSV file
    public static List<String[]> readCSV() {
        List<String[]> products = new ArrayList<>();
        File file = new File(CSV_FILE_PATH);
        System.out.println("Attempting to read file: " + file.getAbsolutePath());

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                products.add(line.split(",", 4)); // Split into 4 fields: category, name, price, description
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    // Method to append a new row to the CSV file
    public static void appendToCSV(String[] newRow) {
        try (FileWriter fw = new FileWriter(CSV_FILE_PATH, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(String.join(",", newRow));
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // New method specifically for User1cart.csv
    public static void appendToCart(String[] newRow) {
        String filePath = "C:\\Users\\Irfan\\OneDrive\\Desktop\\FRESHLOKAL\\BACKEND\\Database\\User1cart.csv";
        try (FileWriter fw = new FileWriter(filePath, true);
            BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(String.join(",", newRow));
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to convert CSV data to JSON format
    public static String convertToJson(List<String[]> products) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < products.size(); i++) {
            String[] product = products.get(i);
            json.append("{")
                .append("\"category\": \"").append(product[0]).append("\",")
                .append("\"name\": \"").append(product[1]).append("\",")
                .append("\"price\": \"").append(product[2]).append("\",")
                .append("\"description\": \"").append(product[3]).append("\"")
                .append("}");
            if (i < products.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }

    public static boolean deleteFromCSV(String productName) {
        File file = new File(CSV_FILE_PATH);
        List<String[]> products = readCSV();
        boolean productFound = false;
    
        // Filter out the product to delete
        List<String[]> updatedProducts = new ArrayList<>();
        for (String[] product : products) {
            if (!product[1].equalsIgnoreCase(productName)) {
                updatedProducts.add(product);
            } else {
                productFound = true; // Mark the product as found
            }
        }
    
        // Write the updated products back to the CSV file
        if (productFound) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                for (String[] product : updatedProducts) {
                    bw.write(String.join(",", product));
                    bw.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
        return productFound; // Return true if the product was found and deleted
    }   
}

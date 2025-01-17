package com.freshlokal.backend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;

public class CSVUtils {
    private static final String CSV_FILE_PATH = "Database/Products.csv";
    private static final String USERS_CSV_PATH = "Database/Users.csv";

    public static boolean isAdmin() {
        String currentUser = getCurrentUser();
        return currentUser != null && currentUser.equalsIgnoreCase("admin");
    }

    public static double calculateTotalPrice(String userCartFile) {
        List<String[]> cartItems = readCartCSV(userCartFile);
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
    
    // Method to read a specific user's cart CSV file
    public static List<String[]> readCartCSV(String userCartFile) {
        List<String[]> cartItems = new ArrayList<>();
        File file = new File(userCartFile);
    
        if (!file.exists()) {
            System.out.println("❌ Cart file does not exist: " + userCartFile);
            return cartItems;
        }
    
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("🔍 Raw Cart Line: " + line); // Debugging
                String[] values = line.split(",", 6); // Ensure split into 6 fields
                if (values.length == 6) {
                    cartItems.add(values);
                } else {
                    System.out.println("❌ Skipping invalid line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cartItems;
    }
    
    

    public static void checkUsersFile() {
        File file = new File(USERS_CSV_PATH);
        System.out.println("Users.csv Exists: " + file.exists());
        System.out.println("Users.csv Path: " + file.getAbsolutePath());
    }

    public static void testReadUsersFile() {
        File file = new File(USERS_CSV_PATH);
        System.out.println("Users.csv Exists: " + file.exists());
        System.out.println("Users.csv Path: " + file.getAbsolutePath());
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("Read User: " + line);
            }
        } catch (IOException e) {
            System.out.println("Error reading Users.csv: " + e.getMessage());
        }
    }
    
    // Read all users from Users.csv
    public static List<String> readUsers() {
        List<String> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_CSV_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                users.add(line.trim()); // Trim spaces to prevent errors
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Loaded Users: " + users); // Debugging Line
        return users;
    }

    public static boolean isUserValid(String username, String password) {
        List<String[]> users = readUsersWithPasswords();
        System.out.println("Checking Login: '" + username + "', Password: '" + password + "'");
        System.out.println("Loaded Users: " + users);
    
        for (String[] user : users) {
            System.out.println("Checking User: " + user[0] + ", " + user[1]);
            if (user[0].equalsIgnoreCase(username) && user[1].equals(password)) {
                System.out.println("✅ User Exists: " + username);
                return true;
            }
        }
    
        System.out.println("❌ User Not Found: " + username);
        return false;
    }
    

    // Read users and passwords from Users.csv
    public static List<String[]> readUsersWithPasswords() {
        List<String[]> users = new ArrayList<>();
        File file = new File(USERS_CSV_PATH);
        
        if (!file.exists()) {
            System.out.println("ERROR: Users.csv file is missing at " + file.getAbsolutePath());
            return users;
        }
    
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("Loaded User: " + line); // Debug
                users.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
    

    // Store logged-in user in a session file
    public static void setCurrentUser(String username) {
        File file = new File("Database/currentUser.txt"); // Ensure the directory exists
        try {
            // Create the file if it doesn't exist
            file.getParentFile().mkdirs();
            file.createNewFile();
    
            // Write the logged-in user to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, false)); // Overwrite existing data
            writer.write(username);
            writer.newLine();
            writer.close();
            System.out.println("User logged in: " + username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Get the currently logged-in user
    public static String getCurrentUser() {
        File file = new File("Database/currentUser.txt");
        if (!file.exists()) return null; // No user logged in
    
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    

     // Clear logged-in user (logout function)
    public static void logoutUser() {
        File file = new File("Database/currentUser.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            writer.write(""); // Overwrite file with empty content
            System.out.println("User logged out. currentUser.txt cleared.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

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
    String csvFile = "Database/Products.csv"; // Ensure correct path

    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            if (values.length >= 5) { // Ensure correct data structure
                products.add(values);
            } else {
                System.out.println("⚠ Skipping invalid entry: " + Arrays.toString(values));
            }
        }
    } catch (IOException e) {
        System.out.println("❌ Error reading CSV file: " + e.getMessage());
    }

    System.out.println("✅ Finished Reading CSV: " + products.size() + " products loaded.");
    return products;
}

    

    // Method to append a new row to the CSV file
    public static void appendToCSV(String[] newRow) {
        try (FileWriter fw = new FileWriter(CSV_FILE_PATH, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            if (newRow.length != 5) {
                System.out.println("❌ Error: Invalid product format!");
                return;
            }
            bw.write(String.join(",", newRow));
            bw.newLine();
            System.out.println("✅ Product added: " + String.join(",", newRow));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    // New method specifically for User1cart.csv
    public static void appendToCart(String[] newRow, String filePath) {
        try (FileWriter fw = new FileWriter(filePath, true);
            BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(String.join(",", newRow));
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void appendToUserCart(String[] newRow, String userCartFile) {
        List<String[]> cartItems = readCartCSV(userCartFile);
        boolean itemExists = false;
    
        for (String[] item : cartItems) {
            if (item[1].equalsIgnoreCase(newRow[1])) { // Check if item name matches
                int existingQuantity = Integer.parseInt(item[5]); // Get current quantity
                int newQuantity = existingQuantity + Integer.parseInt(newRow[5]); // Add new quantity
                item[5] = String.valueOf(newQuantity); // Update quantity
                itemExists = true;
                break;
            }
        }
    
        if (!itemExists) {
            cartItems.add(newRow); // Add new item if not found
        }
    
        // Write the updated cart back to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(userCartFile))) {
            for (String[] item : cartItems) {
                bw.write(String.join(",", item));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    

    // Method to convert CSV data to JSON format
    public static String convertToJson(List<String[]> cartItems) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < cartItems.size(); i++) {
            String[] item = cartItems.get(i);
            if (item.length < 6) {
                System.out.println("❌ Skipping invalid cart item: " + String.join(",", item));
                continue; // Skip if incomplete
            }
    
            json.append("{")
                .append("\"category\": \"").append(item[0]).append("\",")
                .append("\"name\": \"").append(item[1]).append("\",")
                .append("\"price\": \"").append(item[2]).append("\",")
                .append("\"description\": \"").append(item[3]).append("\",")
                .append("\"image\": \"").append(item[4]).append("\",")
                .append("\"quantity\": \"").append(item[5]).append("\"")
                .append("}");
    
            if (i < cartItems.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        System.out.println("✅ JSON Response: " + json);
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
    
    public static boolean registerUser(String username, String password) {
        List<String[]> users = readUsersWithPasswords();
        
        // Check if user already exists
        for (String[] user : users) {
            if (user[0].equalsIgnoreCase(username)) {
                System.out.println("User already exists: " + username);
                return false; // User already exists
            }
        }
    
        // Add new user to Users.csv
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_CSV_PATH, true))) {
            writer.write(username + "," + password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Failed to add user
        }
    
        // Create user's cart file (username_cart.csv)
        String cartFilePath = "Database/" + username + "_cart.csv";
        createFile(cartFilePath);
    
        // Create user's purchase file (username_purchase.csv)
        String purchaseFilePath = "Database/" + username + "_purchase.csv";
        createFile(purchaseFilePath);
    
        System.out.println("User registered successfully: " + username);
        return true;
    }
    
    // Helper method to create a file if it doesn't exist
    private static void createFile(String filePath) {
        File file = new File(filePath);
        try {
            if (file.createNewFile()) {
                System.out.println("Created file: " + filePath);
            } else {
                System.out.println("File already exists: " + filePath);
            }
        } catch (IOException e) {
            System.out.println("Error creating file: " + filePath);
            e.printStackTrace();
        }
    }
    
    public static boolean deleteUser(String username) {
    File file = new File(USERS_CSV_PATH);
    List<String[]> users = readUsersWithPasswords();
    boolean userFound = false;

    // Remove user from the list
    List<String[]> updatedUsers = users.stream()
            .filter(user -> !user[0].equalsIgnoreCase(username))
            .collect(Collectors.toList());

        // If user was removed, update Users.csv
        if (updatedUsers.size() < users.size()) {
            userFound = true;
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                for (String[] user : updatedUsers) {
                    bw.write(String.join(",", user));
                    bw.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return userFound;
    }

    public static boolean updateProduct(String oldProductName, String newCategory, String newName, String newPrice, String newDescription) {
        List<String[]> products = readCSV();
        boolean productFound = false;
    
        for (String[] product : products) {
            if (product[1].equalsIgnoreCase(oldProductName)) { // Check by product name
                product[0] = newCategory; // Update category
                product[1] = newName; // Update name
                product[2] = newPrice; // Update price
                product[3] = newDescription; // Update description
                productFound = true;
                break;
            }
        }
    
        if (productFound) {
            // Overwrite existing Products.csv instead of creating a new one
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("Database/Products.csv", false))) { // false to overwrite
                for (String[] product : products) {
                    bw.write(String.join(",", product));
                    bw.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
        return productFound;
    }

    // Convert users to JSON format (hiding passwords)
    public static String convertUsersToJson(List<String[]> users) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < users.size(); i++) {
            String[] user = users.get(i);
            json.append("{")
                .append("\"username\": \"").append(user[0]).append("\"")
                .append("}"); // Do NOT include passwords in response
            if (i < users.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }

    public static boolean deleteFromCart(String userCartFile, String productName) {
        File file = new File(userCartFile);
        if (!file.exists()) {
            System.out.println("Cart file does not exist: " + userCartFile);
            return false;
        }
    
        List<String[]> cartItems = readCartCSV(userCartFile);
        boolean itemFound = false;
    
        List<String[]> updatedCart = new ArrayList<>();
        for (String[] item : cartItems) {
            if (!item[1].equalsIgnoreCase(productName)) {
                updatedCart.add(item);
            } else {
                itemFound = true; // Found and removed the item
            }
        }
    
        if (itemFound) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                for (String[] item : updatedCart) {
                    bw.write(String.join(",", item));
                    bw.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
        return itemFound;
    }

    public static double calculateItemTotalPrice(String userCartFile, String productName) {
        List<String[]> cartItems = readCartCSV(userCartFile);
        
        for (String[] item : cartItems) {
            if (item[1].equalsIgnoreCase(productName)) { // Check if product name matches
                try {
                    double pricePerUnit = Double.parseDouble(item[2]); // Price
                    int quantity = Integer.parseInt(item[4]); // Quantity
                    return pricePerUnit * quantity; // Total price for this item
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format for: " + productName);
                    return -1; // Indicate error
                }
            }
        }
    
        return -1; // Product not found
    }
    
    // ✅ Convert product list to JSON for the Shop page
    public static String convertProductsToJson(List<String[]> products) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < products.size(); i++) {
            String[] product = products.get(i);
            
            if (product.length < 5) {
                System.out.println("❌ Error: Product missing fields!");
                continue; // Skip if incomplete
            }
    
            json.append("{")
                .append("\"category\": \"").append(product[0]).append("\",")
                .append("\"name\": \"").append(product[1]).append("\",")
                .append("\"price\": \"").append(product[2]).append("\",")
                .append("\"description\": \"").append(product[3]).append("\",")
                .append("\"image\": \"").append(product[4]).append("\"")
                .append("}");
    
            if (i < products.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }
    
    public static void appendToPurchaseCSV(String[] newRow, String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(String.join(",", Arrays.stream(newRow)
        .map(value -> value.contains(",") ? "\"" + value + "\"" : value) // Wrap fields with commas in quotes
        .toArray(String[]::new)
));

            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void clearFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String convertToJsonWithTransaction(List<String[]> purchaseList) {
        List<Map<String, String>> formattedPurchases = new ArrayList<>();
        
        for (String[] purchase : purchaseList) {
            Map<String, String> purchaseData = new HashMap<>();
            purchaseData.put("transactionId", purchase[0]);
            purchaseData.put("category", purchase[1]);
            purchaseData.put("name", purchase[2]);
            purchaseData.put("price", purchase[3]);
            purchaseData.put("description", purchase[4]);
            purchaseData.put("image", purchase[5]);
            purchaseData.put("quantity", purchase[6]);  // ✅ Fix: Ensure Quantity is included
            
            formattedPurchases.add(purchaseData);
        }

        Gson gson = new Gson();
        return gson.toJson(formattedPurchases);
    }


    
}

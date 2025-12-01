# FreshLokals – Malaysian Local Grocery Marketplace 

## 📌 Introduction
FreshLokals is an e-commerce prototype built by a group of Year 2 Computer Science students.  
It highlights Malaysian local grocery products through a simple and responsive interface.

The project uses **Vite + React** for the frontend and **Java** for the backend.  
We also added **PayPal Sandbox integration** to show how an online payment works without using real money.

**Deployment Note**❗❗❗
- Frontend-only deployment.
- Best viewed on laptop or tablet.
- Mobile layout not supported yet.

## ✨ Features
- Clean and responsive UI built with React
- Local Malaysian grocery categories
- CSV-based product and order storage
- PayPal Sandbox checkout demo
- Simple and lightweight architecture

## 💳 PayPal Sandbox Integration
This project uses PayPal Sandbox to simulate online payments.  
It walks through the full flow:
1. Add items to cart  
2. Proceed to checkout  
3. Complete mock payment  
4. Return status to the website  

This gives a realistic experience without using real money.

## 🌿 About the Project
FreshLokals is designed to support Malaysian local sellers.  
The platform gives users a simple way to browse items, add them to cart and simulate a full checkout flow.  
The project structure is intentionally straightforward to make it easy for students to read and extend.

## Download & Installation Guide

## 🧰 Prerequisites
Before running the project, make sure you have:
- Java 17+
- Maven
- Node.js (LTS)
- Git

---

## ⬇️ Installation

1. Open your terminal and navigate to your desired directory.
2. Clone the repository using:
   ```sh
   git clone https://github.com/Adm-Irf/Fresh-Lokals.git
   ```
3. Navigate to the project directory:
   ```sh
   cd FreshLokals
   ```

---

## Running the Backend
Before starting the frontend, make sure the backend is running.

### 1️⃣ Navigate to the Backend Folder
```sh
cd BACKEND
```

### 2️⃣ Install Dependencies and Build
```sh
mvn clean install
```

### 3️⃣ Run the Java Backend
```sh
mvn exec:java -Dexec.mainClass="com.freshlokal.backend.App"
```

✅ The backend should start at:  
**`http://localhost:8080`**

---

## Running the Frontend
### 1️⃣ Open a New Terminal and Navigate to the Frontend Folder
```sh
cd ../FRONTEND/vite-project
```

### 2️⃣ Install Dependencies
```sh
npm install
```

### 3️⃣ Start the Frontend
```sh
npm run dev
```

✅ The frontend should start at:  
**`http://localhost:5173`** (or another available port)

---

## 📝 Notes
- Start the backend before running the frontend.
- API calls are made to: `http://localhost:8080/`
- Project works best with Node LTS, Java 17+ and Maven installed.

If you have any questions or suggestions, feel free to reach out!

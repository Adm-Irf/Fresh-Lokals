# FreshLokals - Malaysian Local Grocery Marketplace

## Introduction
Hai! We are Year 2 Computer Science students, and this is our **FreshLokals** e-commerce project! FreshLokals is an online marketplace dedicated to promoting Malaysian local grocery products. The platform is built using **Vite with React** for the frontend and uses **CSV files** for data storage with JAVA Backend.

## Project Features
- A user-friendly interface for browsing and purchasing local food and ingredients.
- **Navbar, Landing Page, and PayPal integration** for seamless transactions.
- Categories include fresh produce, pantry staples, and more.
- CSV-based data storage for product listings and order management.

---

## About the Project
FreshLokals aims to provide a convenient platform for users to support **local businesses** by purchasing fresh and authentic Malaysian ingredients. Our approach ensures an easy-to-use interface, smooth transactions, and reliable product listings. 

## Download & Installation Guide

### Prerequisites
Ensure you have the following installed on your system:
- **Java 17+** (for backend)
- **Maven** (for managing backend dependencies)
- **Node.js** (LTS version recommended for frontend)
- **Git** (for cloning the repository)

---

## Steps to Download

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

## Notes
- Ensure **Java 17+**, **Maven**, and **Node.js (18+)** are installed.
- The backend must be running before accessing the frontend.
- API requests are made to `http://localhost:8080/`.

---

If you have any questions or suggestions, feel free to reach out!

**Happy Shopping!** 🛒🇲🇾

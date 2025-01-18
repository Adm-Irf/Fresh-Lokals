import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";  
import "./styles/App.css";
import Navbar from "./components/Navbar";
import LandingPage from "./LandingPage";
import About from "./About";
import Farmer from "./Farmer";
import Login from "./Login"; 
import Footer from "./components/Footer";
import Shop from "./Shop";
import CartPage from "./CartPage";  // Ensure this import is correct


function App() {
    return (
        <Router>
            <Navbar />
            <div className="app-container">
                <Routes>  {/* ✅ Wrap with Routes */}
                    <Route path="/" element={<LandingPage />} />
                    <Route path="/about" element={<About />} />
                    <Route path="/farmer" element={<Farmer />} />
                    <Route path="/Shop" element={<Shop />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/cart" element={<CartPage/>} />
                </Routes>
                <Footer />
            </div>
        </Router>
    );
}

export default App;
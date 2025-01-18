import React from "react";
import { BrowserRouter as Router } from "react-router-dom"; // ✅ Import Router
import "./styles/App.css";
import Navbar from "./components/Navbar";
import LandingPage from "./LandingPage";

function App() {
    return (
        <Router>
            <Navbar />
            <div className="app-container"> 
                <LandingPage />
            </div>
        </Router>
    );
}

export default App;

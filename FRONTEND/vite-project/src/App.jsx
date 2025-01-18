import React from "react";
import { BrowserRouter as Router } from "react-router-dom"; // ✅ Import Router
import "./styles/App.css";
import Navbar from "./components/Navbar";
import LandingPage from "./LandingPage";

function App() {
    return (
        <Router>  {/* ✅ Wrap the entire app in Router */}
            <Navbar />
            <LandingPage />
        </Router>
    );
}

export default App;

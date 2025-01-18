import React from "react";
import { Link } from "react-router-dom";  // ✅ Keep React Router for internal links
import "../styles/Navbar.css";

const Navbar = () => {
    return (
        <nav className="navbar">
            <div className="left">
                <Link to="/login" className="signin-button">Sign In</Link>
            </div>
            <div className="right">
                <Link to="/">Home</Link>
                <Link to="/Shop">Shop</Link>
                <Link to="/farmer">Farmer</Link>
                <Link to="/about">About</Link>  

            </div>
        </nav>
    );
};

export default Navbar;

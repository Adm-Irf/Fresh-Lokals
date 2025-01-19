import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import "../styles/Navbar.css";

const Navbar = () => {
    const [username, setUsername] = useState(localStorage.getItem("user") || null);
    const navigate = useNavigate();

    // ✅ Update state when user logs in
    useEffect(() => {
        const handleStorageChange = () => {
            setUsername(localStorage.getItem("user") || null);
        };

        // ✅ Listen for custom login event
        window.addEventListener("userLoggedIn", handleStorageChange);
        window.addEventListener("storage", handleStorageChange);

        return () => {
            window.removeEventListener("userLoggedIn", handleStorageChange);
            window.removeEventListener("storage", handleStorageChange);
        };
    }, []);

    // ✅ Logout function
    const handleLogout = () => {
        if (window.confirm("Are you sure you want to log out?")) {
            localStorage.removeItem("user"); // ✅ Clear session
            setUsername(null); // ✅ Update UI immediately
            navigate("/"); // ✅ Redirect to homepage
        }
    };

    return (
        <nav className="navbar">
            <div className="left">
                {/* ✅ Show username if logged in, otherwise show Sign In */}
                {username ? (
                    <button className="username-button" onClick={handleLogout}>
                        {username} ▼
                    </button>
                ) : (
                    <Link to="/login" className="signin-button">Sign In</Link>
                )}
            </div>
            <div className="right">
                <Link to="/">Home</Link>
                <Link to="/shop">Shop</Link>
                <Link to="/farmer">Farmer</Link>
                <Link to="/about">About</Link>  
                <Link to="/cart">Cart</Link>
            </div>
        </nav>
    );
};

export default Navbar;
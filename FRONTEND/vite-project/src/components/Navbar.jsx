import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import "../styles/Navbar.css";

const Navbar = () => {
    const navigate = useNavigate();
    const [username, setUsername] = useState(localStorage.getItem("user") || null);

    useEffect(() => {
        const handleStorageChange = () => {
            setUsername(localStorage.getItem("user") || null);
        };

        window.addEventListener("userLoggedIn", handleStorageChange);
        window.addEventListener("storage", handleStorageChange);

        return () => {
            window.removeEventListener("userLoggedIn", handleStorageChange);
            window.removeEventListener("storage", handleStorageChange);
        };
    }, []);

    const handleLogout = () => {
        if (window.confirm("Are you sure you want to log out?")) {
            localStorage.removeItem("user");
            localStorage.removeItem("adminToken");
            setUsername(null);
            window.dispatchEvent(new Event("storage"));
            navigate("/");
        }
    };

    return (
        <nav className="navbar">
            <div className="left">
                {username ? (
                    <button className="username-button" onClick={handleLogout}>
                        {username} <span className="dropdown-arrow">▼</span>
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
                {username && <Link to="/purchases">Purchases</Link>} {/* ✅ Add Purchases Button */}
                <Link to="/cart">Cart</Link>
            </div>
        </nav>
    );
};

export default Navbar;

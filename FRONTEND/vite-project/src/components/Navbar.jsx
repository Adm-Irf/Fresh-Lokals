import React from "react";
import { Link } from "react-router-dom";
import "../styles/Navbar.css"; // Ensure this file exists

const Navbar = ({ user }) => {
    return (
        <nav className="navbar">
            {/* Left Side: User Info or Sign In */}
            <div className="left">
                {user ? (
                    <span>Welcome, {user.name}</span>
                ) : (
                    <Link to="/signin" className="sign-in-btn">Sign In</Link>
                )}
            </div>

            {/* Right Side: Navigation Links */}
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

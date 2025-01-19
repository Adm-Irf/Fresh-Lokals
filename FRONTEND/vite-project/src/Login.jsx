import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./styles/Login.css";

const Login = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState(null);
    const [isSigningUp, setIsSigningUp] = useState(false); // ✅ Toggle between login & signup
    const navigate = useNavigate();

    // ✅ Handle Login
    const handleLogin = async (e) => {
        e.preventDefault();
        setError(null);
    
        try {
            const response = await fetch("http://localhost:8080/login", {
                method: "POST",
                headers: {
                    "Content-Type": "text/plain",
                },
                body: `${username},${password}`,
            });
    
            const data = await response.json();
    
            if (response.ok) {
                alert(data.message);
                localStorage.setItem("user", username);
    
                // ✅ Notify Navbar to update
                window.dispatchEvent(new Event("userLoggedIn"));
    
                // ✅ Redirect admin to AdminDashboard
                if (username.toLowerCase() === "admin") {
                    navigate("/admin");
                } else {
                    navigate("/shop");
                }
            } else {
                setError(data.message || "Invalid credentials.");
            }
        } catch (err) {
            console.error("🚨 Server error:", err);
            setError("Server error. Please try again later.");
        }
    };    

    // ✅ Handle Signup
    const handleSignup = async (e) => {
        e.preventDefault();
        setError(null);

        try {
            const response = await fetch("http://localhost:8080/signup", {
                method: "POST",
                headers: {
                    "Content-Type": "text/plain",
                },
                body: `${username},${password}`,
            });

            const data = await response.json();

            if (response.ok) {
                alert("Signup successful! Logging you in...");

                // ✅ Automatically log in the user
                localStorage.setItem("user", username);
                window.dispatchEvent(new Event("userLoggedIn"));

                navigate("/shop"); // ✅ Redirect after signup
            } else {
                setError(data.message || "Signup failed.");
            }
        } catch (err) {
            console.error("🚨 Signup error:", err);
            setError("Server error. Please try again later.");
        }
    };

    return (
        <div className="login-container">
            <h2>{isSigningUp ? "Sign Up" : "Sign In"}</h2> {/* ✅ Switch title dynamically */}
            
            <form onSubmit={isSigningUp ? handleSignup : handleLogin}>
                <div className="input-group">
                    <label>Username</label>
                    <input
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>

                <div className="input-group">
                    <label>Password</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>

                <button type="submit" className="login-button">
                    {isSigningUp ? "Sign Up" : "Login"}
                </button>
            </form>

            {error && <p className="error-message">{error}</p>} {/* ✅ Show error if exists */}

            <p className="toggle-text">
                {isSigningUp ? "Already have an account?" : "Don't have an account?"}
                <button className="toggle-button" onClick={() => setIsSigningUp(!isSigningUp)}>
                    {isSigningUp ? "Sign In" : "Sign Up"}
                </button>
            </p>
        </div>
    );
};

export default Login;
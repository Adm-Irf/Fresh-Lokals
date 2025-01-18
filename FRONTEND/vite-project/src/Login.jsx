import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./styles/Login.css"

const Login = () => {
    const [username, setUsername] = useState(""); // Changed to username
    const [password, setPassword] = useState("");
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        setError(null);
    
        console.log("🔥 handleLogin function is triggered!"); // ✅ Debugging
    
        try {
            console.log(`Attempting login for: ${username}, Password: ${password}`); // ✅ Debugging
    
            const response = await fetch("http://localhost:8080/login", {
                method: "POST",
                headers: {
                    "Content-Type": "text/plain",
                },
                body: `${username},${password}`, // ✅ Ensure correct format
            });
    
            console.log("🔥 Request sent! Waiting for response..."); // ✅ Debugging
    
            const data = await response.json();
            console.log("🔥 Login response:", data); // ✅ Debugging response
    
            if (response.ok) {
                alert(data.message);
                localStorage.setItem("user", username); // ✅ Store current user
                navigate("/shop"); // ✅ Redirect after login
            } else {
                setError(data.message || "Invalid credentials.");
            }
        } catch (err) {
            console.error("🚨 Server error:", err);
            setError("Server error. Please try again later.");
        }
    };
    

    return (
        <form onSubmit={handleLogin}>  {/* ✅ Ensure handleLogin is triggered on submit */}
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

        <button type="submit" className="login-button">Login</button>
    </form>

    );
};

export default Login;

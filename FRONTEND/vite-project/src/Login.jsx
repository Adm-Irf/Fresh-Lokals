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

        try {
            const response = await fetch("http://localhost:8000/auth/login", {
                method: "POST",
                headers: {
                    "Content-Type": "text/plain",
                },
                body: `${username},${password}`, // Sending raw text as expected by Java backend
            });

            const data = await response.json();

            if (response.ok) {
                alert(data.message);
                localStorage.setItem("currentUser", username); // Store login state
                navigate("/shop"); // Redirect to dashboard
            } else {
                setError(data.message);
            }
        } catch (err) {
            setError("Server error. Please try again later.");
        }
    };

    return (
        <div className="login-container">
            <h2>Sign In</h2>
            {error && <p className="error">{error}</p>}
            <form onSubmit={handleLogin}>
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

            <p>Don't have an account? <a href="/signup">Sign Up</a></p>
        </div>
    );
};

export default Login;

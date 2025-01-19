import React from "react";
import { Link } from "react-router-dom";

const OrderSuccess = () => {
    return (
        <div style={{ textAlign: "center", marginTop: "50px" }}>
            <h2>🎉 Order Placed Successfully!</h2>
            <p>Thank you for shopping with FreshLokals.</p>
            <p>Your payment was successful, and we are preparing your order.</p>
            <Link to="/" style={{ textDecoration: "none", color: "white", background: "green", padding: "10px 20px", borderRadius: "5px" }}>
                Return to Home
            </Link>
        </div>
    );
};

export default OrderSuccess;
